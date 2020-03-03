package com.example.demo.controllers;

import com.example.demo.dto.requests.ChangeUserRoleRequest;
import com.example.demo.dto.responses.*;
import com.example.demo.exceptions.*;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.OkResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
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

    @GetMapping("/search/{username}/{projectId}")
    public ResponseEntity<Response> searchByUsername(@PathVariable String username, @PathVariable Long projectId, Authentication authentication) throws NoUserSearchResult {
        List<User> users = userService.searchByUsername(username, projectId);
        return new ResponseEntity<>(new OkResponse(users), HttpStatus.OK);
    }

    @GetMapping("/all/{projectId:\\d+}")
    public ResponseEntity<Response> findAllByUserId(@PathVariable Long projectId) throws UserNotFound {
        List<ProjectUserResponse> users = userService.findAllByProjectId(projectId);
        return new ResponseEntity<>(new OkResponse(users), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Response> changeCurrentUserData(@RequestBody User user, Authentication authentication) throws UserNotFound, UserAlreadyExists {
        UserResponse userResponse = new UserResponse(userService.update(user, authentication));
        return new ResponseEntity<>(new OkResponse(userResponse), HttpStatus.OK);
    }

    @PutMapping("/role")
    public ResponseEntity<Response> changeUserRole(@RequestBody ChangeUserRoleRequest changeUserRoleRequest, Authentication authentication) throws UserNotFound, UserAlreadyExists, RoleNotFound {
        userService.changeUserRole(changeUserRoleRequest);
        return new ResponseEntity<>(new OkResponse(null), HttpStatus.OK);
    }

    @PutMapping("set-fb-token")
    public ResponseEntity<Response> setFbToken ( @RequestBody String fbToken, Authentication authentication) throws UserAlreadyExists {
        User currentUser = userService.setFbToken(fbToken, authentication);
        userService.save(currentUser);
        return new ResponseEntity<>(new OkResponse(currentUser), HttpStatus.OK);
    }
}