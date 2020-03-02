package com.example.demo.services;

import com.example.demo.dto.responses.ProjectUserResponse;
import com.example.demo.exceptions.NoUserSearchResult;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, projectUserRoleLinkService, roleService);
    }

    @Mock
    UserRepository userRepository;

    @Mock
    ProjectUserRoleLinkService projectUserRoleLinkService;

    @Mock
    RoleService roleService;

    @Test
    public void testSave() throws UserAlreadyExists {
        User user = new User();
        userService.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void testFindById() throws UserNotFound {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User actual = userService.findById(1L);
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByIdFail() throws UserNotFound {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        userService.findById(2L);
    }

    @Test
    public void findByUsername() throws UserNotFound {
        User user = new User();
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        User actual = userService.findByUsername("John");
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByUsernameFail() throws UserNotFound {
        when(userRepository.findByUsername("John")).thenReturn(Optional.empty());
        userService.findByUsername("John");
    }

    @Test
    public void findByEmail() throws UserNotFound {
        User user = new User();
        when(userRepository.findByEmail("John@gmail.com")).thenReturn(Optional.of(user));
        User actual = userService.findByEmail("John@gmail.com");
        Assert.assertEquals(actual, user);
    }

    @Test(expected = UserNotFound.class)
    public void testFindByEmailFail() throws UserNotFound {
        when(userRepository.findByEmail("John@gmail.com")).thenReturn(Optional.empty());
        userService.findByEmail("John@gmail.com");
    }

    @Test
    public void testSearchByUsername() throws NoUserSearchResult {
        List<User> userList = new ArrayList<>();
        when(userRepository.findByUsernameContaining("John")).thenReturn(Optional.of(userList));
        List<User> actual = userService.searchByUsername("John", 1L);
        Assert.assertEquals(actual, userList);
    }

    @Test(expected = NoUserSearchResult.class)
    public void testSearchByUsernameFail() throws NoUserSearchResult {
        when(userRepository.findByUsernameContaining("John")).thenReturn(Optional.empty());
        userService.searchByUsername("John", 1L);
    }

    @Test
    public void testFindAllByProjectId() throws UserNotFound {
        List<ProjectUserRoleLink> projectUserRoleLinkList = new ArrayList<>();
        when(projectUserRoleLinkService.findAllByProjectId(1L)).thenReturn(projectUserRoleLinkList);
        List<ProjectUserResponse> projectUserResponseList = projectUserRoleLinkList
                .stream()
                .map(purl -> {
                    User user = purl.getUser();
                    ProjectUserResponse projectUserResponse = new ProjectUserResponse();
                    projectUserResponse.setId(user.getId());
                    projectUserResponse.setUsername(user.getUsername());
                    projectUserResponse.setFirst_name(user.getFirst_name());
                    projectUserResponse.setLast_name(user.getLast_name());
                    projectUserResponse.setEmail(user.getEmail());
                    projectUserResponse.setRoleId(purl.getRole().getId());
                    return projectUserResponse;
                })
                .collect(Collectors.toList());

        List<ProjectUserResponse> actual = userService.findAllByProjectId(1L);
        Assert.assertEquals(actual, projectUserResponseList);
    }

    @Test
    public void testFindAllByProjectIdFail() throws UserNotFound {
        when(projectUserRoleLinkService.findAllByProjectId(1L)).thenReturn(Collections.emptyList());
        userService.findAllByProjectId(1L);
    }

}