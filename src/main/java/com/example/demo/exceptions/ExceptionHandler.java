package com.example.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFoundException() {
        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<String> userAlreadyExistsException() {
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WrongAuthenticationData.class)
    public ResponseEntity<String> wrongAuthenticationData() {
        return new ResponseEntity<>("Username or password is wrong", HttpStatus.NOT_FOUND);
    }
}
