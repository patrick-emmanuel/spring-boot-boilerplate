package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.security.TokenAuthenticationService;
import com.springboilerplate.springboilerplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class AuthenticationController {

    private UserService userService;
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public AuthenticationController(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @RequestMapping(method = RequestMethod.POST, path="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws Exception {
        final User registered = userService.saveUser(user, RoleType.USER);
        registered.setExpires(System.currentTimeMillis() + TokenAuthenticationService.TEN_DAYS);
        String token = tokenAuthenticationService.createUserToken(registered);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(TokenAuthenticationService.AUTH_HEADER_NAME, token);
        return new ResponseEntity<>("Registered and Logged in", responseHeaders, HttpStatus.CREATED);
    }
}
