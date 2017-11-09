package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.stubs.PasswordResetTokenStubs;
import com.springboilerplate.springboilerplate.stubs.RoleStubs;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class RoleMocks {

    public void initMocks(RoleRepository roleRepository){
        when(roleRepository.findByName(anyString())).thenReturn(RoleStubs.generateOptionalRole());
    }
}
