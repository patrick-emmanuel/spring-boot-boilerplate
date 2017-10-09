package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.model.User;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler {
    public static void handleSuccessLogin(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication, CustomUserService userService,
                                          TokenAuthenticationService tokenAuthenticationService) throws ServletException, IOException {
        // Lookup the complete User object from the database and create an Authentication for it
        final User authenticatedUser = userService.loadUserByUsername(authentication.getName());

        // Add UserAuthentication to the response
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
        tokenAuthenticationService.addAuthentication(response, userAuthentication);
    }
}
