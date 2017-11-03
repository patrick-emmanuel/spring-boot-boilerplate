package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.HibernateSearch.HibernateSearchService.UserSearchService;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.security.TokenAuthenticationService;
import com.springboilerplate.springboilerplate.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserSearchService userSearchService;

    @Autowired
    public UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService,
                          UserSearchService userSearchService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userSearchService = userSearchService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws Exception {
        final User registeredUser = userService.saveUser(user, RoleType.USER);
        //move this line of code to the service.
        registeredUser.setExpires(System.currentTimeMillis() + TokenAuthenticationService.TEN_DAYS);
        String token = tokenAuthenticationService.createUserToken(registeredUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(TokenAuthenticationService.AUTH_HEADER_NAME, token);
        return new ResponseEntity<>("Registered and Logged in", responseHeaders, HttpStatus.CREATED);
    }

    //TODO Enable pagination on hibernate search
    @GetMapping(path = "/search")
    public ResponseEntity<?> searchUser(@RequestParam("keyword") String keyword) throws Exception{
        List<User> users = userSearchService.findUsersByKeyword(keyword);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/hello")
    public String getHello(){
        return "hello";
    }
}
