//package com.springboilerplate.springboilerplate.controller;
//
//import com.springboilerplate.springboilerplate.model.User;
//import com.springboilerplate.springboilerplate.repository.UserRepository;
//import com.springboilerplate.springboilerplate.security.AccountCredentials;
//import com.springboilerplate.springboilerplate.service.UserService;
//import com.springboilerplate.springboilerplate.utils.JsonUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.nio.charset.Charset;
//
//import static org.junit.Assert.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//    @Autowired
//    private UserRepository userRepository;
//    @MockBean
//    private UserService userService;
//
//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));
//
//    private MockMvc mockMvc;
//
//
//    @Before
//    @Rollback
//    public void setup() throws Exception {
//        User userToRegister = new User("User", "Patrick", "user@yahoo.com",
//                "17e5b5d9c47c4665151de2a6942484a2a5af8f522a8336d9356cdd99ebdb088e1ab23e296c5094d9");
//        userRepository.save(userToRegister);
//    }
//
//    @Test
//    public void loginUser() throws Exception {
//        AccountCredentials credentials = new AccountCredentials("user@yahoo.com", "password");
//        String authHeader = mockMvc.perform(get("/login")
//                .content(JsonUtils.json())
//                .contentType(contentType))
//                .andExpect(status().isOk()).andReturn()
//                .getResponse()
//                .getHeader("AUTHORIZATION");
//        assertNotNull(authHeader);
//    }
//}