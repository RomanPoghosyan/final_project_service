package com.example.demo.exceptions;


import com.example.demo.dto.responses.BadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
        return new ResponseEntity<>(new BadResponse(Arrays.asList("User already exists")), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadResponse> badCredentialsException(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Wrong username or password")), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Unauthorized.class)
    public ResponseEntity<BadResponse> unauthorized(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Unauthorized")), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProjectNotFound.class)
    public ResponseEntity<BadResponse> projectNotFoundException(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Project not found")), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProjectsByUserIdNotFound.class)
    public ResponseEntity<BadResponse> projectsByUserIdNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("User doesn't have any project")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotificationNotFound.class)
    public ResponseEntity<BadResponse> notificationByNotificationIdNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("There isn't notification by given id!")), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoUserSearchResult.class)
    public ResponseEntity<BadResponse> noUserSearchResult(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("No results")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PrivilegeNotFound.class)
    public ResponseEntity<BadResponse> privilegeNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Privilege not found!")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProjectUserRoleLinkNotFound.class)
    public ResponseEntity<BadResponse> projectUserRoleLinkNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Something went wrong!")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RoleNotFound.class)
    public ResponseEntity<BadResponse> roleNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Role not found!")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<BadResponse> taskNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Tasks not found!")), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TaskStatusNotFound.class)
    public ResponseEntity<BadResponse> taskStatusNotFound(){
        return new ResponseEntity<>(new BadResponse(Arrays.asList("Something went wrong!")), HttpStatus.OK);
    }
}
