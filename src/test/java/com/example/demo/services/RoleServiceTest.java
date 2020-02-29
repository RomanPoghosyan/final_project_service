package com.example.demo.services;

import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.models.Role;
import com.example.demo.repos.PrivilegeRepository;
import com.example.demo.repos.ProjectRepository;
import com.example.demo.repos.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    RoleRepository roleRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    PrivilegeRepository privilegeRepository;

    @DisplayName("Return task by id")
    @Test
    public void testFindById() throws RoleNotFound {
        RoleService roleService = new RoleService(roleRepository, projectRepository, privilegeRepository);
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Role actual = roleService.findById(1L);
        Assert.assertEquals(actual, role);
    }

    @Test(expected = RoleNotFound.class)
    public void testFindByIdFail() throws RoleNotFound {
        RoleService roleService = new RoleService(roleRepository, projectRepository, privilegeRepository);
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        roleService.findById(1L);
    }

    @Test
    public void testSave() {
        RoleService roleService = new RoleService(roleRepository, projectRepository, privilegeRepository);
        Role role = new Role();
        roleService.save(role);
        verify(roleRepository).save(role);
    }
}
