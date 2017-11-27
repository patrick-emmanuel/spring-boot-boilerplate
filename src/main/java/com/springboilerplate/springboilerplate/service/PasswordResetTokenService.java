package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.model.User;

import javax.mail.MessagingException;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user) throws MessagingException;

    boolean validateResetToken(Long userId, String token);
}
