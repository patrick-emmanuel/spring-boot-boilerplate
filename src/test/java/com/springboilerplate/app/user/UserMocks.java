package com.springboilerplate.app.user;

import com.springboilerplate.app.role.RoleType;

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
