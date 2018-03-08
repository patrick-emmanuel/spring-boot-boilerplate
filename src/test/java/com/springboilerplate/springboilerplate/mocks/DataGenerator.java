package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.app.role.RoleType;
import com.springboilerplate.springboilerplate.app.role.Role;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.role.RoleRepository;
import com.springboilerplate.springboilerplate.app.user.UserRepository;
import com.springboilerplate.springboilerplate.app.role.RoleStubs;
import com.springboilerplate.springboilerplate.app.user.UserStubs;

import java.util.Optional;

public class DataGenerator {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public static class Builder {
        private UserRepository userRepository;
        private RoleRepository roleRepository;

        public Builder(UserRepository userRepository, RoleRepository roleRepository) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
        }
        public DataGenerator build() {
            return new DataGenerator(this);
        }
    }

    private DataGenerator(Builder builder) {
        userRepository = builder.userRepository;
        roleRepository = builder.roleRepository;
    }

    public User createUser(int i) {
        User user = UserStubs.generateUser(i);
        return userRepository.save(user);
    }

    public User createUser() {
        User user = UserStubs.generateUser();
        user.setRole(createRole(RoleType.USER));
        return userRepository.save(user);
    }

    public Role createRole(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findByName(roleType.name());
        if(optionalRole.isPresent()){
            return optionalRole.get();
        }
        Role role = RoleStubs.generateRole();
        return roleRepository.saveAndFlush(role);
    }
}

