package com.example.demo.controllers;

import com.example.demo.auth.CustomUser;
import com.example.demo.auth.JwtHelper;
import com.example.demo.auth.JwtUserDetailsService;
import com.example.demo.models.User;
import com.example.demo.dto.requests.LoginRequest;
import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.dto.responses.MeResponse;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
@RequestMapping("/auth")
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
    public ResponseEntity<Response> me() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        return new ResponseEntity<>(new OkResponse(new MeResponse(user.getId(), user.getEmail(), user.getUsername())), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody SignupRequest signupRequest) throws Exception {
        User user = new User();
        user.setFirst_name(signupRequest.getFirst_name());
        user.setLast_name(signupRequest.getLast_name());
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFb_token(signupRequest.getFb_token());
        userService.save(user);
        return this.login(new LoginRequest(signupRequest.getUsername(), signupRequest.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) throws Exception {
        this.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtHelper.generateToken(userDetails);
        Map<String, String> body = new HashMap<String, String>() {{
            put("token", token);
        }};
        return new ResponseEntity<>(new OkResponse(body), HttpStatus.ACCEPTED);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
    }
}
