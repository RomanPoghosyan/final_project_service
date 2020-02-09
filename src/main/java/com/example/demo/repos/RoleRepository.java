package com.example.demo.repos;

import com.example.demo.models.Project;
import com.example.demo.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findById(Long id);
}
