package com.example.demo.controllers;

import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.RolesResponse;
import com.example.demo.exceptions.NotFoundAnyPrivileges;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.models.Privilege;
import com.example.demo.services.RoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

public class RoleControllerTest {

    RoleController roleController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        roleController = new RoleController(roleService);
    }

    @Mock
    RoleService roleService;

    @Mock
    Authentication authentication;

    @Test
    public void testGetRolesByProjectId() throws ProjectNotFound {
        List<RolesResponse> rolesResponses = new ArrayList<>();
        when(roleService.findByProjectId(1L)).thenReturn(rolesResponses);
        ResponseEntity<Response> actual = roleController.getRolesByProjectId(1L, authentication);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), rolesResponses);
    }

    @Test
    public void testGetRolesByProjectIdFail() throws ProjectNotFound {
        List<RolesResponse> rolesResponses = new ArrayList<>();
        when(roleService.findByProjectId(1L)).thenReturn(rolesResponses);
        ResponseEntity<Response> actual = roleController.getRolesByProjectId(1L, authentication);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), rolesResponses);
    }

    @Test
    public void testGetPrivileges() throws NotFoundAnyPrivileges {
        List<Privilege> privileges = new ArrayList<>();
        when(roleService.getAllPrivileges()).thenReturn(privileges);
        ResponseEntity<Response> responseEntity = roleController.getPrivileges();
        Assert.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getBody(), privileges);
    }

    @Test
    public void testGetPrivilegesFalse() throws NotFoundAnyPrivileges {
        List<Privilege> privileges = new ArrayList<>();
        when(roleService.getAllPrivileges()).thenReturn(privileges);
        ResponseEntity<Response> responseEntity = roleController.getPrivileges();
        Assert.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getBody(), privileges);
    }
}
