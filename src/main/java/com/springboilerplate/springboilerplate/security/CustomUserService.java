package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.app.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserService extends UserDetailsService {
    User loadUserByUsername(String username);
}
