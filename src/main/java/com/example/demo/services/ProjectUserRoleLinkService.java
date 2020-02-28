package com.example.demo.services;

import com.example.demo.exceptions.ProjectUserRoleLinkNotFound;
import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectUserRoleLinkRepository;
import com.example.demo.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public ProjectUserRoleLink findById ( Long id ) throws ProjectUserRoleLinkNotFound {
        return projectUserRoleLinkRepository.findById(id).orElseThrow(ProjectUserRoleLinkNotFound::new);
    }

    public List<ProjectUserRoleLink> findAllByUserId (Long id) throws ProjectsByUserIdNotFound {
        return projectUserRoleLinkRepository.findByUserId(id).orElseThrow(ProjectsByUserIdNotFound::new);
    }

    public List<Long> findAllUsersIdsByProjectId (Long id) {
        return projectUserRoleLinkRepository.findByProjectId(id).orElse(new ArrayList<>())
                .stream()
                .map(purl -> purl.getUser().getId())
                .collect(Collectors.toList());
    }

}

