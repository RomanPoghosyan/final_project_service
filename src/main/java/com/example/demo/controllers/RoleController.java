package com.example.demo.controllers;

import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.UserResponse;
import com.example.demo.exceptions.NotFoundAnyPrivileges;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.User;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Response> getRolesByProjectId(@PathVariable Long projectId, Authentication authentication) throws ProjectNotFound {
        return new ResponseEntity<>(new OkResponse(roleService.findByProjectId(projectId)), HttpStatus.OK);
    }

    @GetMapping("/privileges")
    public ResponseEntity<Response> getPrivileges() throws NotFoundAnyPrivileges {
        return new ResponseEntity<>(new OkResponse(roleService.getAllPrivileges()), HttpStatus.OK);
    }
}