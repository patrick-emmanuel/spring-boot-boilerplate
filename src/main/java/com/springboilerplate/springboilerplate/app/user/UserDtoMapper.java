
package com.springboilerplate.springboilerplate.app.user;

import com.springboilerplate.springboilerplate.app.user.UserDto;
import com.springboilerplate.springboilerplate.app.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    private ModelMapper modelMapper;
    public UserDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User toUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}