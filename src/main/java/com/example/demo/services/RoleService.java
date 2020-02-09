package com.example.demo.services;

import com.example.demo.models.Project;
import com.example.demo.models.Role;
import com.example.demo.repos.ProjectRepository;
import com.example.demo.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save (Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> findById ( Long id ) {
        return roleRepository.findById(id);
    }
}

