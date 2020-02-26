package com.example.demo.repos;

import com.example.demo.models.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Optional<Privilege> findById(Long id);
    Iterable<Privilege> findAll();
    Optional<List<Privilege>> findByIdIn(List<Long> privilegesIds);
}
