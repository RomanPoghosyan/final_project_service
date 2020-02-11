package com.example.demo.services;

import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectUserRoleLinkRepository;
import com.example.demo.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class ProjectUserRoleLinkService {

    private final ProjectUserRoleLinkRepository projectUserRoleLinkRepository;

    @Autowired
    public ProjectUserRoleLinkService(ProjectUserRoleLinkRepository projectUserRoleLinkRepository) {
        this.projectUserRoleLinkRepository = projectUserRoleLinkRepository;
    }

    public ProjectUserRoleLink save (ProjectUserRoleLink projectUserRoleLink) {
        return projectUserRoleLinkRepository.save(projectUserRoleLink);
    }

    public ProjectUserRoleLink add (Project project, User user, Role role){
        ProjectUserRoleLink purl = new ProjectUserRoleLink();
        purl.setRole(role);
        purl.setUser(user);
        purl.setProject(project);
        return projectUserRoleLinkRepository.save(purl);
    }

    public Optional<ProjectUserRoleLink> findById ( Long id ) {
        return projectUserRoleLinkRepository.findById(id);
    }




    public Optional<List<ProjectUserRoleLink>> findAllByUserId (Long id) {
        return projectUserRoleLinkRepository.findByUserId(id);
    }
}

