package com.example.demo.repos;

import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface ProjectUserRoleLinkRepository extends CrudRepository<ProjectUserRoleLink, Long> {
    Optional<ProjectUserRoleLink> findById(Long id);
    Optional<List<ProjectUserRoleLink>> findByUserId(Long id);
    Optional<List<ProjectUserRoleLink>> findByProjectId(Long id);
}
