package com.springboilerplate.springboilerplate.app.role;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;
    private Role roleToPersist;

    @Before
    public void setUp() throws Exception {
        roleToPersist = RoleStubs.generateRole();
    }


    @Test
    public void findByNameShouldReturnRole() throws Exception {
        entityManager.persistAndFlush(roleToPersist);

        Optional<Role> optionalRole = roleRepository.findByName("NEW_ROLE");

        assertThat(optionalRole.isPresent()).isTrue();
        assertThat(optionalRole.map(Role::getName).orElse("No role.")).isEqualTo(roleToPersist.getName());
    }

    @Test
    public void findByNameWhenRoleIsInvalidShouldReturnNoRole() throws Exception {
        entityManager.persistAndFlush(roleToPersist);

        Optional<Role> optionalRole = roleRepository.findByName("NON_EXISTENT_ROLE");

        assertThat(optionalRole.isPresent()).isFalse();
    }

}