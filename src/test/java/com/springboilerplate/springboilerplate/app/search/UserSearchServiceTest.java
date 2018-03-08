package com.springboilerplate.springboilerplate.app.search;

import com.springboilerplate.springboilerplate.mocks.DataGenerator;
import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.role.RoleRepository;
import com.springboilerplate.springboilerplate.app.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserSearchServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HibernateSearchService hibernateSearchService;

    private UserSearchService userSearchService;

    @Before
    @Rollback
    public void setUp() throws Exception {
        userSearchService = new UserSearchService(hibernateSearchService);
        DataGenerator dataGenerator = new DataGenerator.Builder(userRepository, roleRepository).build();
        User user = dataGenerator.createUser();
    }

    @Test
    @Rollback
    public void findUsersByKeywordShouldReturnListOfUsersWhenFirstnameMatches() throws Exception {
        List<User> users = userSearchService.findUsersByKeyword("Patrick");

        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findUsersByKeywordShouldReturnListOfUsersWhenLastnameMatches() throws Exception {
        List<User> users = userSearchService.findUsersByKeyword("Emmanuel");

        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findUsersByKeywordShouldReturnListOfUsersWhenEmailMatches() throws Exception {
        List<User> users = userSearchService.findUsersByKeyword("email@email.com");

        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findUsersByKeywordShouldReturnListOfUsersWhenNameMatchesFuzzy() throws Exception {
        List<User> users = userSearchService.findUsersByKeyword("Emmanue");

        assertThat(users.size()).isGreaterThan(0);
    }
}