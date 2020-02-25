package com.example.demo.controllers;

import com.example.demo.dto.requests.AddPrivilegeRequest;
import com.example.demo.dto.requests.AddRoleRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.*;
import com.example.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
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

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<Response> addRole(@RequestBody AddRoleRequest addRoleRequest, Authentication authentication) throws ProjectNotFound, PrivilegeNotFound {
        return new ResponseEntity<>(new OkResponse(roleService.add(addRoleRequest)), HttpStatus.OK);
    }

    @PutMapping(consumes={"application/json"})
    public ResponseEntity<Response> changePrivilege(@RequestBody AddPrivilegeRequest addPrivilegeRequest, Authentication authentication) throws ProjectNotFound, PrivilegeNotFound, RoleNotFound {
        return new ResponseEntity<>(new OkResponse(roleService.addPrivilege(addPrivilegeRequest)), HttpStatus.OK);
    }


}