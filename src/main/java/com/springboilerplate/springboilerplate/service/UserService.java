package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.User;


public interface UserService {

    User saveUser(UserDto userDto, RoleType roleType);
}
