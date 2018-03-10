package com.springboilerplate.app.passwordResetToken;

import com.springboilerplate.app.user.UserStubs;
import com.springboilerplate.app.passwordRestToken.PasswordResetToken;
import com.springboilerplate.app.user.User;

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
