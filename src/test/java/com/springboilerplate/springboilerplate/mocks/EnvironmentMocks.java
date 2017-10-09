package com.springboilerplate.springboilerplate.mocks;


import org.springframework.core.env.Environment;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class EnvironmentMocks {
    public void initMocks(Environment environment){
        when(environment.getProperty(anyString())).thenReturn("password_key");
    }
}
