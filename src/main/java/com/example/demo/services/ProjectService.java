package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.models.Project;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectRepository;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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

