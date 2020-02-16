package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.dto.responses.BadResponse;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
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
        return new ResponseEntity<>(new OkResponse((user)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Response> changeCurrentUserData(@RequestBody User user, Authentication authentication) throws UserNotFound {
        User currentUser = userService.findByUsername(authentication.getName());
        if (!(currentUser.getUsername().equals(user.getUsername()))) {
            try {
                userService.findByUsername(user.getUsername());
                return new ResponseEntity<>(new BadResponse(Collections.singletonList("Username already exists.")), HttpStatus.CONFLICT);
            } catch (UserNotFound ignored) {
                currentUser.setUsername(user.getUsername());
            }
        }
        if (!(currentUser.getEmail().equals(user.getEmail()))) {
            try {
                userService.findByEmail(user.getEmail());
                return new ResponseEntity<>(new BadResponse(Collections.singletonList("Email already exists.")), HttpStatus.CONFLICT);
            } catch (UserNotFound ignored) {
                currentUser.setEmail(user.getEmail());
            }
        }
        currentUser.setFirst_name(user.getFirst_name());
        currentUser.setLast_name(user.getLast_name());
        currentUser.setLocation(user.getLocation());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        return new ResponseEntity<>(new OkResponse(currentUser), HttpStatus.OK);
    }
}