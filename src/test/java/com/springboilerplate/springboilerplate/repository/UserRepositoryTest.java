package com.springboilerplate.springboilerplate.repository;

import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    private User userToSave;

    @Before
    public void setUp() throws Exception {
        userToSave  = TestStubs.generateUserWithNoRole();
    }

    @Test
    public void getByEmailShouldReturnUser() throws Exception {
        entityManager.persistAndFlush(userToSave);

        Optional<User> optionalUser = userRepository.getByEmail("email@email.com");

        assertThat(optionalUser.isPresent()).isTrue();
        assertThat(optionalUser.map(User::getEmail).orElse("No email"))
                .isEqualTo(userToSave.getEmail());
    }

    @Test
    public void getByEmailWhenEmailIsInvalidShouldNotReturnUser() throws Exception {
        entityManager.persistAndFlush(userToSave);

        Optional<User> user = userRepository.getByEmail("invalid@email.com");

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void findByIdAndDeletedIsFalseShouldReturnUser() throws Exception {
        User savedUser = entityManager.persistAndFlush(userToSave);

        Optional<User> user = userRepository.findByIdAndDeletedIsFalse(savedUser.getId());

        assertThat(user.isPresent()).isTrue();
    }

    @Test
    public void findByIdAndDeletedIsFalseWhenDeletedIsTrueShouldNotReturnUser() throws Exception {
        userToSave.setDeleted(true);
        User savedUser = entityManager.persistAndFlush(userToSave);

        Optional<User> user = userRepository.findByIdAndDeletedIsFalse(savedUser.getId());

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void getByEmailAndDeletedFalseShouldReturnUser() throws Exception {
        entityManager.persistAndFlush(userToSave);

        Optional<User> user = userRepository.getByEmailAndDeletedFalse("email@email.com");

        assertThat(user.isPresent()).isTrue();
    }

    @Test
    public void getByEmailAndDeletedFalseWhenDeletedIsTrueShouldNotReturnUser() throws Exception {
        userToSave.setDeleted(true);
        entityManager.persistAndFlush(userToSave);

        Optional<User> user = userRepository.getByEmailAndDeletedFalse("email@email.com");

        assertThat(user.isPresent()).isFalse();
    }
}