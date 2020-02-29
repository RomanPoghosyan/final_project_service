package com.example.demo.services;

import com.example.demo.exceptions.NoUserSearchResult;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication authentication;

    @Mock
    ProjectUserRoleLinkService projectUserRoleLinkService;

    @Mock
    RoleService roleService;

    @Test
    public void testSave() throws UserAlreadyExists {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        User user = new User();
        userService.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void testFindById() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User actual = userService.findById(1L);
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByIdFail() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        userService.findById(2L);
    }

    @Test
    public void findByUsername() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        User user = new User();
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        User actual = userService.findByUsername("John");
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByUsernameFail() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        when(userRepository.findByUsername("John")).thenReturn(Optional.empty());
        userService.findByUsername("John");
    }

    @Test
    public void findByEmail() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        User user = new User();
        when(userRepository.findByEmail("John@gmail.com")).thenReturn(Optional.of(user));
        User actual = userService.findByEmail("John@gmail.com");
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByEmailFail() throws UserNotFound {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        when(userRepository.findByEmail("John@gmail.com")).thenReturn(Optional.empty());
        userService.findByEmail("John@gmail.com");
    }

    @Test
    public void testSearchByUsername() throws UserNotFound, NoUserSearchResult {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        List<User> userList = new ArrayList<>();
        when(userRepository.findByUsernameContaining("John")).thenReturn(Optional.of(userList));
        List<User> actual = userService.searchByUsername("John", 1L);
        Assert.assertEquals(actual, userList);
    }

    @Test(expected = UserNotFound.class)
    public void testSearchByUsernameFail() throws UserNotFound, NoUserSearchResult {
        UserService userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
        when(userRepository.findByUsernameContaining("John")).thenReturn(Optional.empty());
        userService.searchByUsername("John", 1L);
    }


}/*@Test
    public void testUpdate() throws UserNotFound, UserAlreadyExists {
        UserService userService = new UserService(userRepository);
        User user = new User();
        user.getFirst_name("John");
        when(userRepository.findByUsername(authentication.getName())).thenReturn(Optional.of(user));
        userService.update(user, authentication);
        verify(userRepository).save(user);
    }*/