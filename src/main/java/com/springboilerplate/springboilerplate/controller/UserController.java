package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.HibernateSearch.HibernateSearchService.UserSearchService;
import com.springboilerplate.springboilerplate.dto.PasswordDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.security.TokenAuthenticationService;
import com.springboilerplate.springboilerplate.service.PasswordResetTokenService;
import com.springboilerplate.springboilerplate.service.UserService;
import com.springboilerplate.springboilerplate.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;
    private UserSearchService userSearchService;

    @Autowired
    public UserController(UserService userService, UserSearchService userSearchService) {
        this.userService = userService;
        this.userSearchService = userSearchService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws Exception {
        userService.saveUser(user, RoleType.USER);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @PostMapping(value = "/savePassword")
    public ResponseEntity<?> savePassword(@Valid PasswordDto passwordDto) {
        User user = SecurityUtils.getLoggedInUser();
        userService.changeUserPassword(user, passwordDto);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
