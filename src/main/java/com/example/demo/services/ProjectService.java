package com.example.demo.services;

import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        projectUserRoleLinkService.add(savedProject, user, role);
        return savedProject;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findById ( Long id ) {
        return projectRepository.findById(id);
    }

    public List<Project> findAllByUserId (Long id) throws ProjectsByUserIdNotFound {
        if (projectUserRoleLinkService.findAllByUserId(id).isPresent()) {
            List<ProjectUserRoleLink> projectUserRoleLinks = projectUserRoleLinkService.findAllByUserId(id).get();

            return projectUserRoleLinks
                    .stream()
                    .map(ProjectUserRoleLink::getProject)
                    .collect(Collectors.toList());
        } else {
            throw new ProjectsByUserIdNotFound();
        }
    }
}

