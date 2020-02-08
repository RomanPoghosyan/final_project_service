//package com.example.demo.services;
//
//import com.example.demo.models.ProjectUserRoleLink;
//import com.example.demo.models.Role;
//import com.example.demo.repos.ProjectUserRoleLinkRepository;
//import com.example.demo.repos.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@Service
//public class ProjectUserRoleLinkService {
//
//    private final ProjectUserRoleLinkRepository projectUserRoleLinkRepository;
//
//    @Autowired
//    public ProjectUserRoleLinkService(ProjectUserRoleLinkRepository projectUserRoleLinkRepository) {
//        this.projectUserRoleLinkRepository = projectUserRoleLinkRepository;
//    }
//
//    public ProjectUserRoleLink save (ProjectUserRoleLink projectUserRoleLink) {
//        return projectUserRoleLinkRepository.save(projectUserRoleLink);
//    }
//
//    public Optional<ProjectUserRoleLink> findById ( Long id ) {
//        return projectUserRoleLinkRepository.findById(id);
//    }
//
//
//
//
////    public Optional<Project> findByUserId (Long id) {
////        return projectRepository.findByProjectUserRoleLinks(id);
////    }
//}
//
