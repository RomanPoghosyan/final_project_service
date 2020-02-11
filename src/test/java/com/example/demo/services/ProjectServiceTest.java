package com.example.demo.services;

import com.example.demo.controllers.ProjectController;
import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.models.responses.Response;
import com.example.demo.repos.ProjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectUserRoleLinkService projectUserRoleLinkService;

    @Mock
    RoleService roleService;

    @Mock
    UserService userService;

    @Mock
    Authentication authentication;

    @Test
    public void testFindById() {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        Project project = new Project();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Optional<Project> actual = projectService.findById(1L);
        Project expected = project;
        Assert.assertEquals(actual.get(), expected);
    }

    @Test
    public void testFindByIdFail() {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);

        when(projectRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Project> actual = projectService.findById(2L);
        Assert.assertEquals(actual, Optional.empty());
    }

    @Test
    public void testSave() {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        Project project = new Project();

        projectService.save(project);
        verify(projectRepository).save(project);
    }

    @Test
    public void testAddProjectSaveSuccess() {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        Project project = new Project();
        User user = new User();
        Role role = new Role();
        when(userService.findByUsername(authentication.getName())).thenReturn(Optional.of(user));
        when(roleService.findById(1L)).thenReturn(Optional.of(role));
        when(projectRepository.save(project)).thenReturn(project);

        projectService.add(project, authentication);

        verify(projectRepository).save(project);
    }

    @Test
    public void testAddPURLAddSuccess() {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        Project project = new Project();
        User user = new User();
        Role role = new Role();
        when(userService.findByUsername(authentication.getName())).thenReturn(Optional.of(user));
        when(roleService.findById(1L)).thenReturn(Optional.of(role));
        when(projectRepository.save(project)).thenReturn(project);

        projectService.add(project, authentication);

        verify(projectUserRoleLinkService).add(project, user, role);
    }

    @Test
    public void testFindAllByUserId() throws ProjectsByUserIdNotFound {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        Project project = new Project();
        ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink();
        projectUserRoleLink.setProject(project);
        List<ProjectUserRoleLink> projectUserRoleLinks = Collections.singletonList(projectUserRoleLink);
        when(projectUserRoleLinkService.findAllByUserId(1L)).thenReturn(Optional.of(projectUserRoleLinks));

        List<Project> actual = projectService.findAllByUserId(1L);
        List<Project> expected = Collections.singletonList(project);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = ProjectsByUserIdNotFound.class)
    public void testFindAllByUserIdFail() throws ProjectsByUserIdNotFound {
        ProjectService projectService = new ProjectService(projectRepository, projectUserRoleLinkService, roleService, userService);
        when(projectUserRoleLinkService.findAllByUserId(1L)).thenReturn(Optional.empty());

        projectService.findAllByUserId(1L);
    }

}
