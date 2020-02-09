package com.example.demo.controllers;

import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.responses.Response;
import com.example.demo.services.ProjectService;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    ProjectService projectService;

    @Mock
    UserService userService;

    @Mock
    RoleService roleService;

    @Mock
    Authentication authentication;

    @Test
    public void testGetByIdSuccess() throws Exception {
        ProjectController projectController = new ProjectController(projectService, userService, roleService);
        Project project = new Project();

        when(projectService.findById(1L)).thenReturn(Optional.of(project));
        ResponseEntity<Response> actual = projectController.getProjectById(1L);
        Project expected = project;
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), expected);
    }

    @Test(expected = ProjectNotFound.class)
    public void testGetByIdFail() throws Exception {
        ProjectController projectController = new ProjectController(projectService, userService, roleService);

        when(projectService.findById(2L)).thenReturn(Optional.empty());
        projectController.getProjectById(2L);
    }

    @Test
    public void testAddProjectSuccess() {
        ProjectController projectController = new ProjectController(projectService, userService, roleService);
        Project project = new Project();

        projectController.addProject(project, authentication);
        verify(projectService).add(project, authentication);
    }

}
