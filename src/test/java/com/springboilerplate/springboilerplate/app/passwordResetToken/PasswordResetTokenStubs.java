package com.springboilerplate.springboilerplate.app.passwordResetToken;

import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordResetToken;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.user.UserStubs;

import java.util.Optional;
import java.util.UUID;

public class PasswordResetTokenStubs {

    public static Optional<PasswordResetToken> generateOptionalPasswordResetToken() {
        User user = UserStubs.generateUser();
        user.setId(1L);
        PasswordResetToken passwordResetToken =
                new PasswordResetToken(UUID.randomUUID().toString(), user);
        return Optional.of(passwordResetToken);
    }

    public static PasswordResetToken generatePasswordResetToken() {
        User user = UserStubs.generateUser();
        user.setId(1L);
        PasswordResetToken passwordResetToken = new PasswordResetToken(UUID.randomUUID().toString(), user);
        return passwordResetToken;
    }
}
