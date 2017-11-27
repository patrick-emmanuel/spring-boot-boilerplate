package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.constants.EnvironmentConstants;
import com.springboilerplate.springboilerplate.dto.PasswordDto;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.mapper.UserDtoMapper;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    private Environment environment;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(Environment environment, RoleRepository roleRepository,
                           UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.environment = environment;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public User saveUser(UserDto userDto, RoleType roleType) {
        User user = userDtoMapper.toUser(userDto);
        String encodedPassword = encodeUserPassword(user);
        user.setPassword(encodedPassword);
        return setUserRole(user, roleType);
    }

    private String encodeUserPassword(User user){
        String passwordKey = environment.getProperty(EnvironmentConstants.PASSWORD_KEY);
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(passwordKey);
        return encoder.encode(user.getPassword().trim());
    }

    @Override
    public void changeUserPassword(User user, PasswordDto passwordDto){
        user.setPassword(passwordDto.getNewPassword());
        userRepository.save(user);
    }

    private User setUserRole(User user, RoleType roleType){
        Optional<Role> optionalRole = roleRepository.findByName(roleType.name());
        optionalRole.ifPresent(user::setRole);
        return userRepository.save(user);
    }
}
