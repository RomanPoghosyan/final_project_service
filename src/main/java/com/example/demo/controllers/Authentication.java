package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.auth.JwtHelper;
import com.example.demo.auth.JwtUserDetailsService;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.models.User;
import com.example.demo.models.requests.LoginRequest;
import com.example.demo.models.requests.SignupRequest;
import com.example.demo.models.responses.MeResponse;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class Authentication {
    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    private final JwtUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @Autowired
    public Authentication(AuthenticationManager authenticationManager, JwtHelper jwtHelper, JwtUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public MeResponse me() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        return new MeResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    @ExceptionHandler({UserAlreadyExists.class})
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setFirst_name(signupRequest.getFirst_name());
        user.setLast_name(signupRequest.getLast_name());
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user = userService.save(user);
        if ( user == null ) {
            return new com.example.demo.exceptions.
                    ExceptionHandler().userAlreadyExistsException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        ResponseEntity entity = this.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if ( entity != null )
            return entity;
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtHelper.generateToken(userDetails);

        Map<String, String> body = new HashMap<String, String>() {{
            put("token", token);
        }};
        return ResponseEntity.ok(body);
    }

    private ResponseEntity<?> authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return null;
        } catch (BadCredentialsException e) {
            return new com.example.demo.exceptions.ExceptionHandler().wrongAuthenticationData();
        }
    }
}
