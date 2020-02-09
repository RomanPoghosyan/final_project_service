package com.example.demo.services;

import com.example.demo.models.Project;
import com.example.demo.repos.ProjectRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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
}
