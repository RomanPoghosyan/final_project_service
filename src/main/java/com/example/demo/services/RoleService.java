package com.example.demo.services;

import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.exceptions.UserNotFound;
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

    public Role findById ( Long id ) throws RoleNotFound {
        if(roleRepository.findById(id).isPresent()){
            return roleRepository.findById(id).get();
        } else {
            throw new RoleNotFound();
        }
    }
}

