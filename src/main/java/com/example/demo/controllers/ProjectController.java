package com.example.demo.controllers;


import com.example.demo.auth.CustomUser;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.models.Project;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.models.responses.OkResponse;
import com.example.demo.models.responses.Response;
import com.example.demo.repos.UserRepository;
import com.example.demo.services.ProjectService;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService projectService;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, RoleService roleService){
        this.projectService = projectService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Response> getById(@PathVariable Long projectId) {
        Optional<Project> project = projectService.findById(projectId);
        return new ResponseEntity<>(new OkResponse(project.get()), HttpStatus.OK);
    }

//    @GetMapping("/all/{userId}")
//    public ResponseEntity<Response> all(@PathVariable Long userId) {
//        return new ResponseEntity<>(new OkResponse(), HttpStatus.OK);
//    }

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<Response> add(@RequestBody Project project, Authentication authentication) throws UserAlreadyExists {
        return new ResponseEntity<>(new OkResponse(projectService.add(project, authentication)), HttpStatus.OK);
    }
}
