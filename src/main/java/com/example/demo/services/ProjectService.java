package com.example.demo.services;

import com.example.demo.auth.CustomUser;
import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRoleLinkService projectUserRoleLinkService;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectUserRoleLinkService projectUserRoleLinkService, RoleService roleService, UserService userService) {

        this.projectRepository = projectRepository;
        this.projectUserRoleLinkService = projectUserRoleLinkService;
        this.roleService = roleService;
        this.userService = userService;
    }


    public Project add(Project project, Authentication authentication) {
        Project savedProject = projectRepository.save(project);
        Role role = roleService.findById(1L).get();
        User user = userService.findByUsername(authentication.getName()).get();
        ProjectUserRoleLink purl = new ProjectUserRoleLink();
        purl.setRole(role);
        purl.setUser(user);
        purl.setProject(savedProject);
        projectUserRoleLinkService.save(purl);
        return savedProject;
    }

    public Project save (Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findById ( Long id ) {
        return projectRepository.findById(id);
    }




//    public Optional<Project> findByUserId (Long id) {
//        return projectRepository.findByProjectUserRoleLinks(id);
//    }
}

