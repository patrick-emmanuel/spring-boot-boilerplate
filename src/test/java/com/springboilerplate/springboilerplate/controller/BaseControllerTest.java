package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.SpringBoilerplateApplication;
import com.springboilerplate.springboilerplate.dto.AccountCredentials;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.security.UserAuthentication;
import com.springboilerplate.springboilerplate.stubs.UserStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
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

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBoilerplateApplication.class)
@WebAppConfiguration
@Transactional
public abstract class BaseControllerTest {

    protected MockMvc mockMvc;
    protected String AUTH_HEADER = "authorization";

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected FilterChainProxy filterChainProxy;
    @Autowired
    protected UserRepository userRepository;
    protected String authHeader;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    protected void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    @Rollback
    public void baseSetup() throws Exception {
        if(userRepository.getByEmail("email@email.com") == null){
            userRepository.save(UserStubs.generateUser());
        }
        AccountCredentials credentials = new AccountCredentials("email@email.com", "Password");
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(filterChainProxy, "/*").build();
        authHeader = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getHeader("authorization");
        setup();
    }

    public abstract void setup() throws Exception;

}
