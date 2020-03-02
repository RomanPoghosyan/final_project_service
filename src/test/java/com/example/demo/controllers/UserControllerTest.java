package com.example.demo.controllers;

import com.example.demo.dto.responses.ProjectUserResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.NoUserSearchResult;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    UserService userService;

    @Mock
    Authentication authentication;

    @Test
    public void testSearchByUsername() throws NoUserSearchResult {
        UserController userController = new UserController(userService);
        List<User> users = new ArrayList<>();
        when(userService.searchByUsername("John", 1L)).thenReturn(users);
        ResponseEntity<Response> actual = userController.searchByUsername("John", 1L, authentication);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), users);
    }

    @Test
    public void testSearchByUsernameFail() throws NoUserSearchResult {
        UserController userController = new UserController(userService);
        when(userService.searchByUsername("John", 1L)).thenReturn(Collections.emptyList());
        userController.searchByUsername("John", 1L, authentication);
    }

    @Test
    public void testFindAllByUserId() throws UserNotFound {
        UserController userController = new UserController(userService);
        List<ProjectUserResponse> projectUserResponses = new ArrayList<>();
        when(userService.findAllByProjectId(1L)).thenReturn(projectUserResponses);
        ResponseEntity<Response> actual = userController.findAllByUserId(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), projectUserResponses);
    }

    @Test
    public void testFindAllByUserIdFail() throws UserNotFound {
        UserController userController = new UserController(userService);
        when(userService.findAllByProjectId(1L)).thenReturn(Collections.emptyList());
        userController.findAllByUserId(1L);
    }
}
