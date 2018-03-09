package com.springboilerplate.springboilerplate.app.role;

import java.util.Optional;

public class RoleStubs {

    public static Role generateRole(){
        return new Role(RoleType.ROLE_USER);
    }
    public static Optional<Role> generateOptionalRole(){
        return Optional.of(new Role(RoleType.ROLE_USER));
    }

}
