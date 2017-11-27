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
    private TokenAuthenticationService tokenAuthenticationService;
    private UserSearchService userSearchService;
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService,
                          UserSearchService userSearchService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userSearchService = userSearchService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws Exception {
        final User registeredUser = userService.saveUser(user, RoleType.USER);
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

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<?> resetPassword() throws Exception{
        User user = SecurityUtils.getLoggedInUser();
        passwordResetTokenService.createPasswordResetTokenForUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //If successfully validated, then the user can update his password.
    @PostMapping(value = "/validateToken")
    public ResponseEntity<Boolean> validateUserPassword(@RequestParam("id") long id,
                                                        @RequestParam("token") String token) {
        boolean valid = passwordResetTokenService.validateResetToken(id, token);
        return new ResponseEntity<>(valid, HttpStatus.OK);
    }

    @PostMapping(value = "/savePassword")
    public ResponseEntity<?> savePassword(@Valid PasswordDto passwordDto) {
        User user = SecurityUtils.getLoggedInUser();
        userService.changeUserPassword(user, passwordDto);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
