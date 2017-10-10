package com.springboilerplate.springboilerplate.repository;

import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
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
@SpringBootTest
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    private Role roleToPersist;

    @Before
    public void setUp() throws Exception {
        roleToPersist = TestStubs.generateRole();
    }


    @Test
    public void findByNameShouldReturnRole() throws Exception {
        entityManager.persistAndFlush(roleToPersist);

        Optional<Role> optionalRole = roleRepository.findByName(RoleType.USER.name());

        assertThat(optionalRole.isPresent()).isTrue();
        assertThat(optionalRole.map(Role::getName).orElse("No role.")).isEqualTo(roleToPersist.getName());
    }

    @Test
    public void findByNameWhenRoleIsInvalidShouldReturnNoRole() throws Exception {
        entityManager.persistAndFlush(roleToPersist);

        Optional<Role> optionalRole = roleRepository.findByName(RoleType.ADMIN.name());

        assertThat(optionalRole.isPresent()).isFalse();
    }

}