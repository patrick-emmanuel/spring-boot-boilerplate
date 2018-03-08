package com.springboilerplate.springboilerplate.app.role;

import java.util.Optional;

public class RoleStubs {

    public static Role generateRole(){
        return new Role("NEW_ROLE");
    }
    public static Optional<Role> generateOptionalRole(){
        return Optional.of(new Role(RoleType.USER.name()));
    }

}
