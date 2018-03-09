package com.springboilerplate.springboilerplate.app.userRole;

import com.springboilerplate.springboilerplate.app.role.Role;
import com.springboilerplate.springboilerplate.app.role.RoleStubs;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.user.UserStubs;

import java.util.ArrayList;
import java.util.List;

public class UserRoleStubs {
    public static UserRole generateUserRole(){
        User user = UserStubs.generateUserWithNoRole();
        Role role = RoleStubs.generateRole();
        return new UserRole(user, role);
    }

    public static List<UserRole> generateUserRoles(){
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(generateUserRole());
        return userRoles;
    }
}
