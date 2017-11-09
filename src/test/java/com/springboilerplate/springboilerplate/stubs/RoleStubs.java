package com.springboilerplate.springboilerplate.stubs;

import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.Role;

import java.util.Optional;

public class RoleStubs {

    public static Role generateRole(){
        return new Role(RoleType.USER.name());
    }
    public static Optional<Role> generateOptionalRole(){
        return Optional.of(new Role(RoleType.USER.name()));
    }

}
