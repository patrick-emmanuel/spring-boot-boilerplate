package com.springboilerplate.springboilerplate.app.user;

        import com.springboilerplate.springboilerplate.app.passwordRestToken.PasswordDto;
        import com.springboilerplate.springboilerplate.app.role.RoleType;
        import com.springboilerplate.springboilerplate.app.role.Role;
        import com.springboilerplate.springboilerplate.app.role.RoleRepository;
        import com.springboilerplate.springboilerplate.app.userRole.UserRole;
        import com.springboilerplate.springboilerplate.app.userRole.UserRoleRepository;
        import com.springboilerplate.springboilerplate.exceptions.RoleDoesNotExistException;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository, UserRoleRepository userRoleRepository, UserDtoMapper userDtoMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public User saveUser(UserDto userDto, RoleType roleType) {
        User user = userDtoMapper.toUser(userDto);
        return setUserRole(user, roleType);
    }

    @Override
    public void changeUserPassword(User user, PasswordDto passwordDto){
        user.setPassword(passwordDto.getNewPassword());
        userRepository.save(user);
    }

    private User setUserRole(User user, RoleType roleType){
        Optional<Role> optionalRole = roleRepository.findByName(roleType);
        Role role = optionalRole.orElseThrow(() -> new RoleDoesNotExistException("There is no role such as: " + roleType.name())) ;
        UserRole userRole = new UserRole(user, role);
        user.addUserRole(userRole);
        return userRepository.save(user);
    }
}
