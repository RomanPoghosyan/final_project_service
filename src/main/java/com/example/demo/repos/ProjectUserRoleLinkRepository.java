package com.example.demo.repos;

import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ProjectUserRoleLinkRepository extends CrudRepository<ProjectUserRoleLink, Long> {
    Optional<ProjectUserRoleLink> findById(Long id);
}
