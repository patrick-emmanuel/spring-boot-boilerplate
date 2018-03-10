package com.springboilerplate.security;

import com.springboilerplate.app.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserService extends UserDetailsService {
    User loadUserByUsername(String username);
}
