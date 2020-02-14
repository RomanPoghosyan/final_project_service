package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.models.User;
import com.example.demo.models.responses.OkResponse;
import com.example.demo.models.responses.Response;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userData")
    public ResponseEntity<Response> getCurrentUserData() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = ((CustomUser) securityContext.getAuthentication().getPrincipal()).getUsername();
        Optional<User> user = userService.findByUsername(username);
        return new ResponseEntity<>(new OkResponse((user)), HttpStatus.OK);
    }
}
