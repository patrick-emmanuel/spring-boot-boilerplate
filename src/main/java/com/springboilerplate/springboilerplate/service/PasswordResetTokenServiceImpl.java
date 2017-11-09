package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.helper.MailService;
import com.springboilerplate.springboilerplate.helper.SecurityHelper;
import com.springboilerplate.springboilerplate.model.PasswordResetToken;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenRepository passwordTokenRepository;
    private MailService mailService;
    private SecurityHelper securityHelper;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordTokenRepository,
                                         MailService mailService, SecurityHelper securityHelper) {
        this.passwordTokenRepository = passwordTokenRepository;
        this.mailService = mailService;
        this.securityHelper = securityHelper;
    }

    @Override
    public void createPasswordResetTokenForUser(User user) throws MessagingException {
        String token = UUID.randomUUID().toString();
        PasswordResetToken userToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(userToken);
        mailService.sendMail(user.getEmail(), "Your reset password is: " + token, "Password Reset Token");
    }

    @Override
    public boolean validatePassword(Long userId, String tokenValue) {
        Optional<PasswordResetToken> optionalToken = passwordTokenRepository.findByToken(tokenValue);
        boolean validToken = optionalToken
                .filter(userToken -> !isTokenExpired(userToken))
                .filter(userToken ->  isTokenValid(userToken, userId))
                .isPresent();
        if(validToken){
            securityHelper.grantUserChangePasswordPrivilege(optionalToken.get());
            return true;
        }
        return false;
    }

    private boolean isTokenValid(PasswordResetToken userToken, Long userId) {
        return Optional.ofNullable(userToken)
                .map(PasswordResetToken::getUser)
                .filter(user -> user.getId().equals(userId))
                .isPresent();
    }
    
    private boolean isTokenExpired(PasswordResetToken userToken){
        Instant currentTime = Instant.now();
        Instant expiryTime = userToken.getExpiryDate();
        return expiryTime.isBefore(currentTime);
    }
}
