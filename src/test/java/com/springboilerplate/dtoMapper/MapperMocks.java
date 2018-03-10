package com.springboilerplate.dtoMapper;

import com.springboilerplate.app.user.UserStubs;
import com.springboilerplate.app.user.UserDto;
import com.springboilerplate.app.user.UserDtoMapper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MapperMocks {

    public void initMocks(UserDtoMapper userDtoMapper){
        when(userDtoMapper.toUser(any(UserDto.class))).thenReturn(UserStubs.generateUser());
    }

}
