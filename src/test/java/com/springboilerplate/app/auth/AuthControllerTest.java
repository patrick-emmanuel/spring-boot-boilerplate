package com.springboilerplate.app.auth;

import com.springboilerplate.app.role.Role;
import com.springboilerplate.app.role.RoleType;
import com.springboilerplate.app.user.User;
import com.springboilerplate.app.userRole.UserRole;
import com.springboilerplate.security.JwtUserDetailsService;
import com.springboilerplate.security.JwtTokenUtil;
import com.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    public void successfulAuthenticationWithAnonymousUser() throws Exception {
        AccountCredentials accountCredentials = new AccountCredentials("email@email.com", "password");
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.getJson(accountCredentials)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void successfulRefreshTokenWithUserRole() throws Exception {
        User user = getUser();
        when(jwtTokenUtil.getEmailFromToken(any())).thenReturn(user.getUsername());
        when(jwtUserDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(user);
        when(jwtTokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);
        mvc.perform(get("/refresh")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void successfulRefreshTokenWithAdminRole() throws Exception {
        User user = getUser();
        when(jwtTokenUtil.getEmailFromToken(any())).thenReturn(user.getUsername());
        when(jwtUserDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(user);
        when(jwtTokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);

        mvc.perform(get("/refresh")
                .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    public void shouldGetUnauthorizedWithAnonymousUser() throws Exception {
        mvc.perform(get("/refresh"))
                .andExpect(status().isUnauthorized());
    }

    private User getUser() {
        Role role = new Role();
        role.setId(0L);
        role.setName(RoleType.ROLE_USER);
        User user = new User();
        user.setEmail("username");
        UserRole userRole = new UserRole(user, role);
        List<UserRole> authorities = Arrays.asList(userRole);
        user.setUserRoles(authorities);
        user.setEnabled(Boolean.TRUE);
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis() + 1000 * 1000));
        return user;
    }
}