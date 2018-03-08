package com.springboilerplate.springboilerplate.app.passwordRestToken;

import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

    Optional<PasswordResetToken> findByToken(String token);
}
