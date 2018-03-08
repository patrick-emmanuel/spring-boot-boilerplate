package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.app.role.RoleType;
import com.springboilerplate.springboilerplate.app.role.Role;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.role.RoleRepository;
import com.springboilerplate.springboilerplate.app.user.UserRepository;
import com.springboilerplate.springboilerplate.app.role.RoleStubs;
import com.springboilerplate.springboilerplate.app.user.UserStubs;
import com.springboilerplate.springboilerplate.app.userRole.UserRole;
import com.springboilerplate.springboilerplate.app.userRole.UserRoleRepository;
import com.springboilerplate.springboilerplate.app.userRole.UserRoleStubs;

import java.util.Optional;

public class DataGenerator {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;


    public static class Builder {
        private UserRepository userRepository;
        private RoleRepository roleRepository;
        private UserRoleRepository userRoleRepository;

        public Builder(UserRepository userRepository, RoleRepository roleRepository) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
        }
        public Builder userRoleRepo(UserRoleRepository userRoleRepository) {
            this.userRoleRepository = userRoleRepository;
            return this;
        }
        public DataGenerator build() {
            return new DataGenerator(this);
        }
    }

    private DataGenerator(Builder builder) {
        userRepository = builder.userRepository;
        roleRepository = builder.roleRepository;
        userRoleRepository = builder.userRoleRepository;
    }

    public User createUser(int i) {
        User user = UserStubs.generateUser(i);
        return userRepository.save(user);
    }

    public User createUser() {
        User user = UserStubs.generateUser();
        user.addUserRole(UserRoleStubs.generateUserRole());
        return userRepository.save(user);
    }

    public UserRole createUserRole(){
        UserRole userRole = UserRoleStubs.generateUserRole();
        return userRoleRepository.save(userRole);
    }



    public Role createRole(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findByName(roleType);
        if(optionalRole.isPresent()){
            return optionalRole.get();
        }
        Role role = RoleStubs.generateRole();
        return roleRepository.saveAndFlush(role);
    }
}

