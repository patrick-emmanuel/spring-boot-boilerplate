package com.springboilerplate.app.user;

import com.springboilerplate.app.userRole.UserRoleStubs;

import java.util.Optional;

public class UserStubs {

    public static User generateUser(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com", UserRoleStubs.generateUserRoles());
    }

    public static User generateUser(int i){
        return new User("Patrick" + i, "Emmanuel" + i,
                "Password", "email@email.com" + i, UserRoleStubs.generateUserRoles());
    }
    public static User generateUserWithEncyptedPassword(){
        return new User("Patrick", "Emmanuel",
                "f1dc2596efa04da1e8652955bcc2aa355e053a7739b2c68a6f99e15aae7bbe8e941b1bdf2dffcf5d", "email@email.com");
    }

    public static Optional<User> generateOptionalUser(){
        return Optional.of(new User("Patrick", "Emmanuel",
                "Password", "email@email.com", UserRoleStubs.generateUserRoles()));
    }
    public static UserDto generateUserDto(){
        return new UserDto("Patrick", "Emmanuel",
                "email@email.com", "Password");
    }

    public static User generateUserWithNoRole(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com");
    }
}
