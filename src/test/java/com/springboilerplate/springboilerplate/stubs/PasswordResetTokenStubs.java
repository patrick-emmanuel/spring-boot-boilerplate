package com.springboilerplate.springboilerplate.stubs;

import com.springboilerplate.springboilerplate.model.PasswordResetToken;
import com.springboilerplate.springboilerplate.model.User;

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
