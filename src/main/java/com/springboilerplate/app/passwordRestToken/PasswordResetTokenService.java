package com.springboilerplate.app.passwordRestToken;

import com.springboilerplate.app.user.User;

import javax.mail.MessagingException;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user) throws MessagingException;

    boolean validateResetToken(Long userId, String token);
}
