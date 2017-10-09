package com.springboilerplate.springboilerplate.mapper;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.modelmapper.ModelMapper;


public class UserDtoMapperTest {

    private ModelMapper mapper = new ModelMapper();
    UserDtoMapper userDtoMapper = new UserDtoMapper(mapper);
    private User user;
    private UserDto userDto;

    @Before
    public void setUp() throws Exception {
        user = TestStubs.generateUser();
        userDto = TestStubs.generateUserDto();
    }

    @Test
    public void toUserDtoWhenUserEmailAndDtoEmailAreSameShouldReturnTrue() throws Exception {
        UserDto userDto =  userDtoMapper.toUserDto(user);

        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void toUserDtoWhenUserPasswordAndDtoPasswordAreSameShouldReturnTrue() throws Exception {
        UserDto userDto =  userDtoMapper.toUserDto(user);

        assertThat(userDto.getPassword()).isEqualTo(user.getPassword());
    }
    @Test
    public void toUserDtoWhenUserFirstnameAndDtoFirstnameAreSameShouldReturnTrue() throws Exception {
        UserDto userDto =  userDtoMapper.toUserDto(user);

        assertThat(userDto.getFirstname()).isEqualTo(user.getFirstname());
    }

    @Test
    public void toUserDtoWhenUserLastnameAndDtoLastnameAreSameShouldReturnTrue() throws Exception {
        UserDto userDto =  userDtoMapper.toUserDto(user);

        assertThat(userDto.getLastname()).isEqualTo(user.getLastname());
    }

    @Test
    public void toUserWhenUserEmailAndDtoEmailAreSameShouldReturnTrue() throws Exception {
        User user =  userDtoMapper.toUser(userDto);

        assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    public void toUserWhenUserFirstnameAndDtoFirstnameAreSameShouldReturnTrue() throws Exception {
        User user =  userDtoMapper.toUser(userDto);

        assertThat(user.getFirstname()).isEqualTo(userDto.getFirstname());
    }

    @Test
    public void toUserWhenUserLastnameAndDtoLastnameAreSameShouldReturnTrue() throws Exception {
        User user =  userDtoMapper.toUser(userDto);

        assertThat(user.getLastname()).isEqualTo(userDto.getLastname());
    }

    @Test
    public void toUserWhenUserPasswordAndDtoPasswordAreSameShouldReturnTrue() throws Exception {
        User user =  userDtoMapper.toUser(userDto);

        assertThat(user.getPassword()).isEqualTo(userDto.getPassword());
    }


}