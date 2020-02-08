package com.example.demo.repos;

import com.example.demo.models.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ProjectRepository extends CrudRepository<Project, Long> {
    Optional<Project> findById(Long id);
}
