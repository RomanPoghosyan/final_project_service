package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.auth.JwtHelper;
import com.example.demo.auth.JwtUserDetailsService;
import com.example.demo.dto.requests.LoginRequest;
import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

public class AuthenticationTest {
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtHelper jwtHelper;

    @Mock
    JwtUserDetailsService jwtUserDetailsService;

    @Mock
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SecurityContextHolder securityContextHolder;

    @Mock
    SignupRequest signupRequest;

    @Mock
    UserDetailsService userDetailsService;

    @Test
    public void signUpTest() throws Exception {
        Authentication authentication = new Authentication(authenticationManager, jwtHelper, jwtUserDetailsService, passwordEncoder, userService);
        User user = new User();
        LoginRequest loginRequest = new LoginRequest(signupRequest.getUsername(), signupRequest.getPassword());
        when(signupRequest.getFirst_name()).thenReturn("John");
        when(signupRequest.getLast_name()).thenReturn("Doe");
        when(signupRequest.getUsername()).thenReturn("JD");
        when(signupRequest.getPassword()).thenReturn("123456");
        userService.save(user);
        ResponseEntity<Response> actual = authentication.signup(signupRequest);
        Assert.assertEquals(actual.getBody().getBody(), loginRequest);
    }

    @Test
    public void loginTest(){
        Authentication authentication = new Authentication(authenticationManager, jwtHelper, jwtUserDetailsService, passwordEncoder, userService);

    }
}
