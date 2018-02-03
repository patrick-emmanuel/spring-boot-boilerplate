package com.springboilerplate.springboilerplate.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PasswordResetTokenControllerTest extends BaseControllerTest {

    @Override
    public void setup() throws Exception {

    }

    @Test
    @Rollback
    public void resetPassword() throws Exception {
        mockMvc.perform(get("/v1/passwordResetToken/resetPassword")
                .header(AUTH_HEADER, authHeader))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void validateUserPassword() throws Exception {
    }

}