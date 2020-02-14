package com.example.demo.repos;

import com.example.demo.models.TaskStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TaskStatusRepository extends CrudRepository<TaskStatus, Long> {
    Optional<TaskStatus> findById(Long id);
}
