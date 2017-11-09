package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.helper.SecurityHelper;
import com.springboilerplate.springboilerplate.model.PasswordResetToken;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class SecurityHelperMocks {

    public void initMocks(SecurityHelper securityHelper){
        Mockito.doNothing().when(securityHelper)
                .grantUserChangePasswordPrivilege(any(PasswordResetToken.class));
    }
}
