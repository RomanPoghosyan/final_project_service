package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.auth.JwtHelper;
import com.example.demo.auth.JwtUserDetailsService;
import com.example.demo.models.User;
import com.example.demo.models.requests.LoginRequest;
import com.example.demo.models.requests.SignupRequest;
import com.example.demo.models.responses.MeResponse;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Authentication(AuthenticationManager authenticationManager, JwtHelper jwtHelper, JwtUserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public MeResponse me() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        return new MeResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        this.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtHelper.generateToken(userDetails);

        Map<String, String> body = new HashMap<String, String>() {{
            put("token", token);
        }};

        return ResponseEntity.ok(body);
    }

    @PostMapping("/logout")
    public String logout() {
        return "asd";
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String authError() {
        return "unauthorized";
    }
}
