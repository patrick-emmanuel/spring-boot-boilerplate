package com.springboilerplate.springboilerplate.stubs;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.model.User;

import java.util.Optional;

public class UserStubs {

    public static User generateUser(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com", RoleStubs.generateRole());
    }

    public static User generateUser(int i){
        return new User("Patrick" + i, "Emmanuel" + i,
                "Password", "email@email.com" + i, RoleStubs.generateRole());
    }

    public static Optional<User> generateOptionalUser(){
        return Optional.of(new User("Patrick", "Emmanuel",
                "Password", "email@email.com", RoleStubs.generateRole()));
    }
    public static UserDto generateUserDto(){
        return new UserDto("Patrick", "Emmanuel",
                "Password", "email@email.com");
    }

    public static User generateUserWithNoRole(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com");
    }
}
