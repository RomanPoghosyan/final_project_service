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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService projectService;
    private UserService userService;
    private RoleService roleService;
    private UserRepository userRepository;
//    private ProjectUserRoleLinkService purlService;


    @Autowired
    public ProjectController(ProjectService projectService, UserService userService, RoleService roleService, UserRepository userRepository){
        this.projectService = projectService;
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
//        this.purlService = purlService;
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
    public ResponseEntity<Response> add(@RequestBody Project project) throws UserAlreadyExists {
        Project p = projectService.save(project);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        User u = userRepository.findById(user.getId()).get();
        Role r = roleService.findById(1L).get();

        u.getProjectRoleMap().put(p, r);

        userRepository.save(u);

        return new ResponseEntity<>(new OkResponse(p), HttpStatus.OK);
    }
}
