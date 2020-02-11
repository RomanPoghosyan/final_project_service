package com.example.demo.services;

import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectUserRoleLinkRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectUserRoleLinkServiceTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    ProjectUserRoleLinkRepository projectUserRoleLinkRepository;

    @Test
    public void saveSuccess(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);
        ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink();

        projectUserRoleLinkService.save(projectUserRoleLink);
        verify(projectUserRoleLinkRepository).save(projectUserRoleLink);
    }

    @Test
    public void findByIdSuccess(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);
        ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink();

        when(projectUserRoleLinkRepository.findById(1L)).thenReturn(Optional.of(projectUserRoleLink));

        Optional<ProjectUserRoleLink> actual = projectUserRoleLinkService.findById(1L);
        Optional<ProjectUserRoleLink> expected = Optional.of(projectUserRoleLink);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdFail(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);

        when(projectUserRoleLinkRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProjectUserRoleLink> actual = projectUserRoleLinkService.findById(1L);
        Optional<ProjectUserRoleLink> expected = Optional.empty();

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findAllByUserIdSuccess(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);
        List<ProjectUserRoleLink> projectUserRoleLinks = Arrays.asList(new ProjectUserRoleLink(), new ProjectUserRoleLink());

        when(projectUserRoleLinkRepository.findByUserId(1L)).thenReturn(Optional.of(projectUserRoleLinks));

        Optional<List<ProjectUserRoleLink>> actual = projectUserRoleLinkService.findAllByUserId(1L);
        Optional<List<ProjectUserRoleLink>> expected = Optional.of(projectUserRoleLinks);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findAllByUserIdFail(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);

        when(projectUserRoleLinkRepository.findByUserId(1L)).thenReturn(Optional.empty());

        Optional<List<ProjectUserRoleLink>> actual = projectUserRoleLinkService.findAllByUserId(1L);
        Optional<List<ProjectUserRoleLink>> expected = Optional.empty();

        Assert.assertEquals(actual, expected);
    }

    @Captor ArgumentCaptor<ProjectUserRoleLink> captor;
    @Test
    public void addSuccess(){
        ProjectUserRoleLinkService projectUserRoleLinkService = new ProjectUserRoleLinkService(projectUserRoleLinkRepository);
        Project project = new Project();
        User user = new User();
        Role role = new Role();

        projectUserRoleLinkService.add(project, user, role);

        verify(projectUserRoleLinkRepository).save(captor.capture());
    }
}
