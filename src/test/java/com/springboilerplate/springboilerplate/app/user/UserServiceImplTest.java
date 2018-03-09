package com.springboilerplate.springboilerplate.app.user;

import com.springboilerplate.springboilerplate.app.userRole.UserRoleRepository;
import com.springboilerplate.springboilerplate.dtoMapper.MapperMocks;
import com.springboilerplate.springboilerplate.app.role.RoleType;
import com.springboilerplate.springboilerplate.app.role.RoleMocks;
import com.springboilerplate.springboilerplate.app.role.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    //dependencies
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserDtoMapper userDtoMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;

    //system under test.
    private UserService userService;

    //Mocks.
    private MapperMocks mapperMocks = new MapperMocks();
    private RoleMocks roleMocks = new RoleMocks();
    private UserMocks userMocks = new UserMocks();

    @Before
    public void setUp() throws Exception {
        mapperMocks.initMocks(userDtoMapper);
        roleMocks.initMocks(roleRepository);
        userMocks.initMocks(userRepository);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        userService = new UserServiceImpl(roleRepository, userRepository, userDtoMapper, passwordEncoder);
    }

    @Test
    public void saveUserShouldSave() throws Exception {
        UserDto userDto = UserStubs.generateUserDto();

        User user = userService.saveUser(userDto, RoleType.ROLE_USER);

        assertThat(user).isNotNull();
    }

}