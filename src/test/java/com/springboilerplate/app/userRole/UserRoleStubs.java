package com.springboilerplate.app.userRole;

import com.springboilerplate.app.role.Role;
import com.springboilerplate.app.role.RoleStubs;
import com.springboilerplate.app.user.User;
import com.springboilerplate.app.user.UserStubs;

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
