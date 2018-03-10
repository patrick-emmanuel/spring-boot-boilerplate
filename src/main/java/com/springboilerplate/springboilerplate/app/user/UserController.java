package com.springboilerplate.springboilerplate.app.user;

import com.springboilerplate.springboilerplate.app.search.UserSearchService;
import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordDto;
import com.springboilerplate.springboilerplate.app.role.RoleType;
import com.springboilerplate.springboilerplate.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
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
        userService.saveUser(user, RoleType.ROLE_USER);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/savePassword")
    public ResponseEntity<?> savePassword(@Valid PasswordDto passwordDto) {
        User user = SecurityUtils.getLoggedInUser();
        userService.changeUserPassword(user, passwordDto);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    //TODO Enable pagination on hibernate search
    @GetMapping(path = "/search")
    public ResponseEntity<?> searchUser(@RequestParam("keyword") String keyword) throws Exception{
        List<User> users = userSearchService.findUsersByKeyword(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/hello")
    public String getHello(){
        return "hey!";
    }
}
