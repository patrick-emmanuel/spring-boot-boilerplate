package com.springboilerplate.security;

import com.springboilerplate.app.user.UserMocks;
import com.springboilerplate.app.user.User;
import com.springboilerplate.app.user.UserRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setUp() throws Exception {
        jwtUserDetailsService = new JwtUserDetailsService(userRepository);
        userMocks.initMocks(userRepository);
    }

    @Test
    public void loadUserByUsername() throws Exception {
        User user = jwtUserDetailsService.loadUserByUsername("username");

        assertThat(user).isNotNull();
        verify(userRepository).getByEmailAndDeletedFalse(anyString());
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void loadUserByUsernameShouldReturnTheUserFirstnameWhenUserIsPresent() throws Exception {
        User user = jwtUserDetailsService.loadUserByUsername("notvalid");

        assertThat(user.getFirstname()).isEqualTo("Patrick");
        verify(userRepository).getByEmailAndDeletedFalse(anyString());
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserIsInvalid() throws Exception {
        when(userRepository.getByEmailAndDeletedFalse(anyString())).thenReturn(Optional.empty());

        thrown.expect(UsernameNotFoundException.class);
        thrown.expectMessage("User with  '" + "username" + "' email not found.");

        User user = jwtUserDetailsService.loadUserByUsername("username");
    }
}