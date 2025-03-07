package com.example.demo.controllers;

import com.example.demo.dto.requests.ColumnReorderRequest;
import com.example.demo.dto.responses.ProjectResponse;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.dto.responses.Response;
import com.example.demo.models.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    ProjectController projectController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        projectController = new ProjectController(projectService, userService, roleService);
    }

    @Mock
    ProjectService projectService;

    @Mock
    UserService userService;

    @Mock
    RoleService roleService;

    @Mock
    Authentication authentication;

    @Mock
    ColumnReorderRequest columnReorderRequest;

    @Test
    public void testGetProjectByIdSuccess() throws Exception {
        ProjectResponse projectResponse = new ProjectResponse();
        when(projectService.getFullProjectInfo(1L, authentication)).thenReturn(projectResponse);
        ResponseEntity<Response> actual = projectController.getProjectById(1L, authentication);
        Assert.assertEquals(actual.getBody().getBody(), projectResponse);
    }

    @Test(expected = ProjectNotFound.class)
    public void testGetProjectByIdFail() throws ProjectNotFound {
        when(projectService.getFullProjectInfo(2L, authentication)).thenThrow(new ProjectNotFound());
        projectController.getProjectById(2L, authentication);
    }

    @Test
    public void testAddProjectSuccess() throws UserNotFound, RoleNotFound {
        Project project = new Project();
        projectController.addProject(project, authentication);
        verify(projectService).add(project, authentication);
    }

    @Test
    public void testFindAllByUserId() throws ProjectsByUserIdNotFound {
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectService.findAllByUserId(1L)).thenReturn(projects);
        ResponseEntity<Response> actual = projectController.findAllByUserId(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), projects);
    }

    @Test(expected = ProjectsByUserIdNotFound.class)
    public void testFindAllByUserIdFail() throws ProjectsByUserIdNotFound {
        when(projectService.findAllByUserId(1L)).thenThrow(new ProjectsByUserIdNotFound());
        projectController.findAllByUserId(1L);
    }

    @Test
    public void testUpdateColumnOrder() throws ProjectNotFound, UserNotFound, RoleNotFound {
        projectController.updateColumnOrder(columnReorderRequest, authentication);
        verify(projectService).updateColumnOrder(columnReorderRequest, authentication);
    }

}
