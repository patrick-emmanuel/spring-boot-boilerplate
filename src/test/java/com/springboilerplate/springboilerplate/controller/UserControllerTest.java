package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.stubs.UserStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest{

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private UserDto userDto;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userDto = UserStubs.generateUserDto();
    }

    @Test
    @Rollback
    public void registerUser() throws Exception {
        this.mockMvc.perform(post("/v1/users/register")
                .content(JsonUtils.json(userDto, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
