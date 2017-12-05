package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.dto.AccountCredentials;
import com.springboilerplate.springboilerplate.stubs.UserStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class LoginTest extends BaseControllerTest{
    @Autowired
    private FilterChainProxy springSecurityFilter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    private User user;

    private MockMvc mockMvc;

    @Before
    @Rollback
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilter, "/*").build();
        userRepository.save(UserStubs.generateUserWithEncyptedPassword());
    }

    @Test
    @Rollback
    public void loginUser() throws Exception {
        AccountCredentials credentials = new AccountCredentials("email@email.com", "Password");
        String authHeader = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getHeader("authorization");
        assertNotNull(authHeader);

        //Access a protected resource to ensure that the jwt authentication is working.
        mockMvc.perform(get("/v1/users/hello")
                .header("authorization", authHeader))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void accessingAProtectedResourceShouldReturn403UnAuthorized() throws Exception {
        mockMvc.perform(get("/v1/users/hello"))
                .andExpect(status().isForbidden());
    }
}