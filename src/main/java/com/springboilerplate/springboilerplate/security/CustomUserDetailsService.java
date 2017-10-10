package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements CustomUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.getByEmailAndDeletedFalse(username);
        optionalUser.ifPresent(this::reflectLogin);
        throw new UsernameNotFoundException("User with  '" + username + "' email not found.");
    }

    private User reflectLogin(User user) {
        //user.setLastLogin(LocalDateTime.now());
        return userRepository.saveAndFlush(user);
    }
}
