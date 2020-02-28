package com.example.demo.controllers;

import com.example.demo.dto.requests.UserSettingsRequest;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.UserResponse;
import com.example.demo.exceptions.UserAlreadyExists;
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

    @Test//???????
    public void getCurrentUserDataTest() throws UserNotFound {
        UserController userController = new UserController(userService);
        User user = new User();
        when(userService.findByUsername("John")).thenReturn(user);
        UserResponse userResponse = new UserResponse(user);
        when(authentication.getName()).thenReturn("John");
        ResponseEntity<Response> actual = userController.getCurrentUserData(authentication);
        Assert.assertEquals(actual.getBody().getBody(), userResponse);
    }

    @Test //???????
    public void changeCurrentUserDataTest() throws UserNotFound, UserAlreadyExists {
        UserController userController = new UserController(userService);
        User user = new User();
        UserResponse userResponse = new UserResponse(user);
        when(userService.update(user, authentication)).thenReturn(user);
        ResponseEntity<Response> expected = userController.changeCurrentUserData(user, authentication);
        Assert.assertEquals(expected.getBody().getBody(), userResponse);
    }
}
