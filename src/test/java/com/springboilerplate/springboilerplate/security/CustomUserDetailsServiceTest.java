package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.mocks.UserMocks;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserMocks userMocks = new UserMocks();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Before
    public void setUp() throws Exception {
        customUserDetailsService = new CustomUserDetailsService(userRepository);
        userMocks.initMocks(userRepository);
    }

    @Test
    public void loadUserByUsername() throws Exception {
        User user = customUserDetailsService.loadUserByUsername("username");

        assertThat(user).isNotNull();
        verify(userRepository).getByEmailAndDeletedFalse(anyString());
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void loadUserByUsernameShouldReturnTheUserFirstnameWhenUserIsPresent() throws Exception {
        User user = customUserDetailsService.loadUserByUsername("notvalid");

        assertThat(user.getFirstname()).isEqualTo("Patrick");
        verify(userRepository).getByEmailAndDeletedFalse(anyString());
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserIsInvalid() throws Exception {
        when(userRepository.getByEmailAndDeletedFalse(anyString())).thenReturn(Optional.empty());

        thrown.expect(UsernameNotFoundException.class);
        thrown.expectMessage("User with  '" + "username" + "' email not found.");

        User user = customUserDetailsService.loadUserByUsername("username");
    }
}