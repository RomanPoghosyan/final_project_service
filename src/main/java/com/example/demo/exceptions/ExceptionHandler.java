package com.example.demo.exceptions;


import com.example.demo.models.responses.BadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFound.class)
    public ResponseEntity<BadResponse> userNotFoundException(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("User not found")), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<BadResponse> userAlreadyExistsException(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("User already exists")), HttpStatus.BAD_REQUEST);
    }
}
