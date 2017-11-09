package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.helper.MailService;
import com.springboilerplate.springboilerplate.helper.SecurityHelper;
import com.springboilerplate.springboilerplate.mocks.MailServiceMocks;
import com.springboilerplate.springboilerplate.mocks.PasswordResetTokenMocks;
import com.springboilerplate.springboilerplate.mocks.SecurityHelperMocks;
import com.springboilerplate.springboilerplate.model.PasswordResetToken;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.PasswordResetTokenRepository;
import com.springboilerplate.springboilerplate.stubs.PasswordResetTokenStubs;
import com.springboilerplate.springboilerplate.stubs.UserStubs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PasswordResetTokenServiceImplTest {

    @MockBean
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @MockBean
    private MailService mailService;
    @MockBean
    private SecurityHelper securityHelper;
    private PasswordResetTokenService passwordResetTokenService;


    PasswordResetTokenMocks passwordResetToken = new PasswordResetTokenMocks();
    MailServiceMocks mailServiceMocks = new MailServiceMocks();
    SecurityHelperMocks securityHelperMocks = new SecurityHelperMocks();
    User user;

    @Before
    public void setUp() throws Exception {
        passwordResetToken.initMocks(passwordResetTokenRepository);
        mailServiceMocks.initMocks(mailService);
        securityHelperMocks.initMocks(securityHelper);
        passwordResetTokenService =
                new PasswordResetTokenServiceImpl(passwordResetTokenRepository, mailService, securityHelper);
        user = UserStubs.generateUser();
    }

    @Test
    public void createPasswordResetTokenForUserShouldCreatePasswordToken() throws Exception {
        passwordResetTokenService.createPasswordResetTokenForUser(user);

        verify(passwordResetTokenRepository).save(any(PasswordResetToken.class));
        verify(mailService).sendMail(anyString(), anyString(), anyString());
    }

    @Test
    public void validatePasswordShouldReturnTrueWhenTokenIsValid() throws Exception {

        boolean valid = passwordResetTokenService.validatePassword(1L, "token");

        assertThat(valid).isTrue();
        verify(securityHelper).grantUserChangePasswordPrivilege(any(PasswordResetToken.class));

    }

    @Test
    public void validatePasswordShouldReturnFalseWhenTokenHasExpired() throws Exception {
        Optional<PasswordResetToken> optionalToken = PasswordResetTokenStubs.generateOptionalPasswordResetToken();
        optionalToken.get().setExpiryDate(Instant.now().minusSeconds(84000L));
        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(optionalToken);

        boolean valid = passwordResetTokenService.validatePassword(1L, "token");

        assertThat(valid).isFalse();
    }

    @Test
    public void validatePasswordShouldReturnFalseWhenTokenIsNull() throws Exception {
        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        boolean valid = passwordResetTokenService.validatePassword(1L, "token");

        assertThat(valid).isFalse();
        verify(passwordResetTokenRepository).findByToken(anyString());
    }

    @Test
    public void validatePasswordShouldReturnFalseWhenIdDontMatch() throws Exception {
        when(passwordResetTokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        boolean valid = passwordResetTokenService.validatePassword(100L, "token");

        assertThat(valid).isFalse();
        verify(passwordResetTokenRepository).findByToken(anyString());
    }
}