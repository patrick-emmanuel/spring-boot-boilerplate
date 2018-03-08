package com.springboilerplate.springboilerplate.app.user;

import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordDto;
import com.springboilerplate.springboilerplate.app.role.RoleType;


public interface UserService {

    User saveUser(UserDto userDto, RoleType roleType);

    void changeUserPassword(User user, PasswordDto passwordDto);
}
