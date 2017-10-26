package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.dto.PageData;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.mapper.EsUserMapper;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.elasticSearch.elasticModel.EsUser;
import com.springboilerplate.springboilerplate.security.TokenAuthenticationService;
import com.springboilerplate.springboilerplate.service.UserService;
import com.springboilerplate.springboilerplate.elasticSearch.elasticService.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;
    private TokenAuthenticationService tokenAuthenticationService;
    private EsUserService esUserService;
    private EsUserMapper esUserMapper;

    @Autowired
    public UserController(UserService userService,
                          TokenAuthenticationService tokenAuthenticationService, EsUserService esUserService, EsUserMapper esUserMapper) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.esUserService = esUserService;
        this.esUserMapper = esUserMapper;
    }

    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws Exception {
        final User registeredUser = userService.saveUser(user, RoleType.USER);
        EsUser esUser = esUserMapper.toEsUser(registeredUser);
        esUserService.createEsUser(esUser, registeredUser.getId());
        registeredUser.setExpires(System.currentTimeMillis() + TokenAuthenticationService.TEN_DAYS);
        String token = tokenAuthenticationService.createUserToken(registeredUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(TokenAuthenticationService.AUTH_HEADER_NAME, token);
        return new ResponseEntity<>("Registered and Logged in", responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping(path="/search")
    public PageData<EsUser> searchUser(@RequestParam(value = "query") String query,
                                        @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber){
        Page<EsUser> usersPage =  esUserService.findUser(query, pageNumber);
        PageData<EsUser> usersPageData = PageData.getDataFromPage(usersPage);
        List<EsUser> users = usersPageData.getContent();
        return new PageData<>(users, usersPageData.getCurrentNumber(), usersPageData.getBeginIndex(), usersPageData.getEndIndex());
    }

    @GetMapping(path = "/hello")
    public String getHello(){
        return "hello";
    }
}
