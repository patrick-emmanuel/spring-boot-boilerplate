package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.stubs.UserStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest{
    private UserDto userDto;

    public void setup() throws Exception {
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
