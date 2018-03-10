package com.springboilerplate.app.user;

import com.springboilerplate.app.role.RoleType;
import com.springboilerplate.app.passwordRestToken.PasswordDto;


public interface UserService {

    User saveUser(UserDto userDto, RoleType roleType);

    void changeUserPassword(User user, PasswordDto passwordDto);
}
