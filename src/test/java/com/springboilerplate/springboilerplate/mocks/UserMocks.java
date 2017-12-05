package com.springboilerplate.springboilerplate.mocks;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.service.UserService;
import com.springboilerplate.springboilerplate.stubs.PasswordResetTokenStubs;
import com.springboilerplate.springboilerplate.stubs.UserStubs;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserMocks {
    public void initMocks(UserRepository userRepository){
        when(userRepository.save(any(User.class))).thenReturn(UserStubs.generateUser());
        when(userRepository.getByEmailAndDeletedFalse(anyString())).thenReturn(UserStubs.generateOptionalUser());
    }
    public void initUserServiceMocks(UserService userService){
        when(doNothing().when(userService).saveUser(any(UserDto.class), any(RoleType.class)));
    }
}
