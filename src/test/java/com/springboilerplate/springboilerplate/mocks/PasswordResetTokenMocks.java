package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.model.PasswordResetToken;
import com.springboilerplate.springboilerplate.repository.PasswordResetTokenRepository;
import com.springboilerplate.springboilerplate.stubs.PasswordResetTokenStubs;

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
