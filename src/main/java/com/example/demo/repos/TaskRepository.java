package com.example.demo.repos;

import com.example.demo.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    Optional<List<Task>> findByAssigneeId(Long userId);

    Optional<List<Task>> findByAssignorId(Long userId);

    Optional<List<Task>> findByProjectId(Long projectId);

    Optional<Task> findById(Long taskId);

    Optional<List<Task>> findTaskByDueDateGreaterThanAndDueDateLessThan(LocalDateTime start, LocalDateTime end);
}
