package com.springboilerplate.springboilerplate.app.user;

import com.springboilerplate.springboilerplate.constants.EnvironmentConstants;
import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordDto;
import com.springboilerplate.springboilerplate.app.role.RoleType;
import com.springboilerplate.springboilerplate.app.role.Role;
import com.springboilerplate.springboilerplate.app.role.RoleRepository;
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
