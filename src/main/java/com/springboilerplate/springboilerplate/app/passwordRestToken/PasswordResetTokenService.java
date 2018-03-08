package com.springboilerplate.springboilerplate.app.passwordRestToken;

import com.springboilerplate.springboilerplate.app.user.User;

import javax.mail.MessagingException;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user) throws MessagingException;

    boolean validateResetToken(Long userId, String token);
}
