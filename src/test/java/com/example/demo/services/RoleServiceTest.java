package com.example.demo.services;

import com.example.demo.dto.requests.AddRoleRequest;
import com.example.demo.dto.responses.AddRoleResponse;
import com.example.demo.exceptions.PrivilegeNotFound;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.models.Privilege;
import com.example.demo.models.Project;
import com.example.demo.models.Role;
import com.example.demo.models.RoleType;
import com.example.demo.repos.PrivilegeRepository;
import com.example.demo.repos.ProjectRepository;
import com.example.demo.repos.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    RoleService roleService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        roleService = new RoleService(roleRepository, projectRepository, privilegeRepository);
    }

    @Mock
    RoleRepository roleRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    PrivilegeRepository privilegeRepository;

    @Mock
    AddRoleRequest addRoleRequest;

    @Mock
    RoleType roleType;

    @DisplayName("Return task by id")
    @Test
    public void testFindById() throws RoleNotFound {
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Role actual = roleService.findById(1L);
        Assert.assertEquals(actual, role);
    }

    @Test(expected = RoleNotFound.class)
    public void testFindByIdFail() throws RoleNotFound {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        roleService.findById(1L);
    }

    @Test
    public void testSave() {
        Role role = new Role();
        roleService.save(role);
        verify(roleRepository).save(role);
    }

    @Test//???
    public void testAddPrivilage() {
    }

    @Test//???
    public void testRemovePrivilage() {
    }

    @Test//???
    public void testFindByProjectId() {
    }

    @Test//???
    public void testGetAllPrivilages() {
    }
}
