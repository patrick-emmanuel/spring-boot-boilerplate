package com.springboilerplate.springboilerplate.dtoMapper;

import com.springboilerplate.springboilerplate.app.user.UserDto;
import com.springboilerplate.springboilerplate.app.user.UserDtoMapper;
import com.springboilerplate.springboilerplate.app.user.UserStubs;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MapperMocks {

    public void initMocks(UserDtoMapper userDtoMapper){
        when(userDtoMapper.toUser(any(UserDto.class))).thenReturn(UserStubs.generateUser());
    }

}
