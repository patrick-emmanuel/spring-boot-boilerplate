package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.helper.SecurityHelper;
import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordResetToken;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;

public class SecurityHelperMocks {

    public void initMocks(SecurityHelper securityHelper){
        Mockito.doNothing().when(securityHelper)
                .grantUserChangePasswordPrivilege(any(PasswordResetToken.class));
    }
}
