package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.stubs.TestStubs;

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
        User user = TestStubs.generateUser(i);
        return userRepository.save(user);
    }

    public User createUser() {
        User user = TestStubs.generateUser();
        user.setRole(createRole(RoleType.USER));
        return userRepository.save(user);
    }

    public Role createRole(RoleType roleType) {
        Optional<Role> optionalRole = roleRepository.findByName(roleType.name());
        if(optionalRole.isPresent()){
            return optionalRole.get();
        }
        Role role = TestStubs.generateRole();
        return roleRepository.saveAndFlush(role);
    }
}

