package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.dto.requests.UserSettingsRequest;
import com.example.demo.dto.responses.*;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.BadResponse;

import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response> getCurrentUserData(Authentication authentication) throws UserNotFound {
        User user = userService.findByUsername(authentication.getName());
        UserResponse userResponse = new UserResponse(user);
        return new ResponseEntity<>(new OkResponse((userResponse)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Response> changeCurrentUserData(@RequestBody User user, Authentication authentication) throws UserNotFound, UserAlreadyExists {
        UserResponse userResponse = new UserResponse(userService.update(user, authentication));
        return new ResponseEntity<>(new OkResponse(userResponse), HttpStatus.OK);
    }
}