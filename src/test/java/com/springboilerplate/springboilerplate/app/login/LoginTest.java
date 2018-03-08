package com.springboilerplate.springboilerplate.app.login;

import com.springboilerplate.springboilerplate.SpringBoilerplateApplication;
import com.springboilerplate.springboilerplate.app.user.UserRepository;
import com.springboilerplate.springboilerplate.security.AccountCredentials;
import com.springboilerplate.springboilerplate.app.user.UserStubs;
import com.springboilerplate.springboilerplate.security.UserToken;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBoilerplateApplication.class)
@WebAppConfiguration
@Transactional
public class LoginTest {

    @Autowired
    private FilterChainProxy filterChainProxy;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private String AUTH_HEADER = "authorization";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

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
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(filterChainProxy, "/*")
                .build();
        //save the user with specified email
        userRepository.save(UserStubs.generateUserWithEncyptedPassword());
    }

    @Test
    @Rollback
    public void loginUser() throws Exception {
        AccountCredentials credentials = new AccountCredentials("email@email.com", "Password");
        String response = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        UserToken token = JsonUtils.getElement(response, UserToken.class);
        String jwtToken = token.getJwtToken();
        assertNotNull(jwtToken);

        //Access a protected resource to ensure that the jwt authentication is working.
        mockMvc.perform(get("/v1/users/hello")
                .header(AUTH_HEADER, jwtToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void accessingAProtectedResourceShouldReturn401UnAuthorized() throws Exception {
        mockMvc.perform(get("/v1/users/hello"))
                .andExpect(status().isForbidden());
    }
}