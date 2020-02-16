package com.example.demo.controllers;


import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.services.ProjectService;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
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
    @Transactional(readOnly = true)
    public ResponseEntity<Response> getProjectById(@PathVariable Long projectId, Authentication authentication) throws ProjectNotFound {
        return new ResponseEntity<>(new OkResponse(projectService.findByIdForResponse(projectId, authentication)), HttpStatus.OK);
    }

    @GetMapping("/all/{userId:\\d+}")
    public ResponseEntity<Response> findAllByUserId(@PathVariable Long userId) throws ProjectsByUserIdNotFound {
        List<Project> projects = projectService.findAllByUserId(userId);
        return new ResponseEntity<>(new OkResponse(projects), HttpStatus.OK);
    }

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<Response> addProject(@RequestBody Project project, Authentication authentication) throws UserNotFound, RoleNotFound {
        return new ResponseEntity<>(new OkResponse(projectService.add(project, authentication)), HttpStatus.OK);
    }
}
