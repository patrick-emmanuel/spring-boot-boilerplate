package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.stubs.PasswordResetTokenStubs;
import com.springboilerplate.springboilerplate.stubs.RoleStubs;
import com.springboilerplate.springboilerplate.stubs.UserStubs;

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

