package com.springboilerplate.app.passwordResetToken;

import com.springboilerplate.app.passwordRestToken.PasswordResetToken;
import com.springboilerplate.app.passwordRestToken.PasswordResetTokenRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PasswordResetTokenMocks {
    public void initMocks(PasswordResetTokenRepository passwordResetTokenRepository){
        when(passwordResetTokenRepository.save(any(PasswordResetToken.class)))
                .thenReturn(PasswordResetTokenStubs.generatePasswordResetToken());
        when(passwordResetTokenRepository.findByToken(anyString()))
                .thenReturn(PasswordResetTokenStubs.generateOptionalPasswordResetToken());
    }
}
