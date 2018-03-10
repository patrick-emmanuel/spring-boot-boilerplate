package com.springboilerplate.utils;


import com.springboilerplate.app.role.Role;
import com.springboilerplate.app.role.RoleType;
import com.springboilerplate.app.user.User;
import com.springboilerplate.app.userRole.UserRole;
import com.springboilerplate.app.userRole.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DbSeed implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DbSeed.class);

    private UserRoleRepository userRoleRepository;

    public DbSeed(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        //Password is 'password' but it has been encrypted.
        logger.info("Loading data...");
        User user = getUser();
        Role roleUser = new Role(RoleType.ROLE_USER);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(roleUser);
        userRoleRepository.save(userRole);
        logger.info("Saved user");

//        User admin = getAdmin();
//        Role roleAdmin = new Role(RoleType.ROLE_ADMIN);
//        UserRole adminRole = new UserRole();
//        userRole.setUser(admin);
//        userRole.setRole(roleAdmin);
//        logger.info("Saved admin");
//
//        userRoleRepository.save(adminRole);
        logger.info("Done.");
    }

    private User getAdmin() {
        User admin = new User();
        admin.setPassword("$2a$10$a1i476ODUG7jqm1x30ThA.v8qYsAQlbLBpfPSW.8ISm2Z8QiC5ASm");
        admin.setEmail("admin@email.com");
        admin.setFirstname("Emmanuel");
        admin.setLastname("Pat");
        admin.setLastLogin(LocalDateTime.now());
        return admin;
    }

    private User getUser() {
        User user = new User();
        user.setPassword("$2a$10$a1i476ODUG7jqm1x30ThA.v8qYsAQlbLBpfPSW.8ISm2Z8QiC5ASm");
        user.setEmail("user@email.com");
        user.setFirstname("Emmanuel");
        user.setLastname("Pat");
        user.setLastLogin(LocalDateTime.now());
        return user;
    }
}
