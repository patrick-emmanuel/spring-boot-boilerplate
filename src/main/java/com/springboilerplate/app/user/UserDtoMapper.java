
package com.springboilerplate.app.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    private ModelMapper modelMapper;
    public UserDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User toUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}