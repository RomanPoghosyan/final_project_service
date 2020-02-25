package com.example.demo.services;

import com.example.demo.dto.requests.AddRoleRequest;
import com.example.demo.dto.responses.AddRoleResponse;
import com.example.demo.dto.responses.RolesResponse;
import com.example.demo.exceptions.NotFoundAnyPrivileges;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final PrivilegeRepository privilegeRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository, ProjectRepository projectRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public Role save (Role role) {
        return roleRepository.save(role);
    }

    public AddRoleResponse add (AddRoleRequest addRoleRequest) throws ProjectNotFound, PrivilegeNotFound {
        Role role = new Role();
        role.setName(addRoleRequest.getName());
        role.setType(RoleType.CUSTOM);
        Project project = projectRepository.findById(addRoleRequest.getProjectId()).orElseThrow(ProjectNotFound::new);
        role.getProjects().add(project);
        List<Privilege> privileges = privilegeRepository.findByIdIn(addRoleRequest.getPrivilegesIds()).orElseThrow(PrivilegeNotFound::new);
        role.setPrivileges(privileges);
        Role newRole = roleRepository.save(role);
        return new AddRoleResponse(newRole.getId(), newRole.getName(), newRole.getType(), addRoleRequest.getPrivilegesIds());
    }

    public Role findById ( Long id ) throws RoleNotFound {
        return roleRepository.findById(id).orElseThrow(RoleNotFound::new);
    }

    public List<RolesResponse> findByProjectId (Long id ) throws ProjectNotFound {
        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFound::new);
        List<RolesResponse> rolesResponses = new ArrayList<>();

        project.getRoles()
                .forEach(role -> {
                    RolesResponse rolesResponse = new RolesResponse();
                    rolesResponse.setId(role.getId());
                    rolesResponse.setName(role.getName());
                    rolesResponse.setType(role.getType());
                    List<Long> privilegesIds = role.getPrivileges()
                                                        .stream()
                                                        .map(Privilege::getId)
                                                        .collect(Collectors.toList());
                    rolesResponse.setPrivileges(privilegesIds);
                    rolesResponses.add(rolesResponse);
                });

        return rolesResponses;
    }

    public List<Privilege> getAllPrivileges() throws NotFoundAnyPrivileges {
        return (List<Privilege>) privilegeRepository.findAll();
    }
}

