package com.springboilerplate.springboilerplate.app.role;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class RoleMocks {

    public void initMocks(RoleRepository roleRepository){
        when(roleRepository.findByName(anyString())).thenReturn(RoleStubs.generateOptionalRole());
    }
}
