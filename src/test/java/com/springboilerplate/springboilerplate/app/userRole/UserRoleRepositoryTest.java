package com.springboilerplate.springboilerplate.app.userRole;

import com.springboilerplate.springboilerplate.app.role.Role;
import com.springboilerplate.springboilerplate.app.role.RoleStubs;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.user.UserStubs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private UserRole userRole;
    private User user;
    private Role role;

    @Before
    public void setUp() throws Exception {
        user = UserStubs.generateUserWithNoRole();
        role = RoleStubs.generateRole();
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(role);
    }

    @Test
    public void findByUserIdAndRoleIdShouldReturnSavedUserRole() {
        entityManager.persistAndFlush(new UserRole(user, role));

        Optional<UserRole> savedUserRole = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());

        assertThat(savedUserRole.isPresent()).isTrue();
        assertThat(savedUserRole.get().getId()).isNotNull();
    }

    @Test
    public void findByUserIdAndRoleIdShouldReturnSavedUserRoleWithCorrectRole() {
        entityManager.persistAndFlush(new UserRole(user, role));

        Optional<UserRole> savedUserRole = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());

        assertThat(savedUserRole.isPresent()).isTrue();
        assertThat(savedUserRole.get().getRole().getId()).isEqualTo(role.getId());
    }

    @Test
    public void findByUserIdAndRoleIdShouldReturnSavedUserRoleWithCorrectUser() {
        entityManager.persistAndFlush(new UserRole(user, role));

        Optional<UserRole> savedUserRole = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());

        assertThat(savedUserRole.isPresent()).isTrue();
        assertThat(savedUserRole.get().getUser().getId()).isEqualTo(user.getId());
    }
}