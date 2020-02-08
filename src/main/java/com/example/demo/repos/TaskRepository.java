package com.example.demo.repos;

import com.example.demo.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    public List<Task> findByAssigneeId (Long userId);
    public List<Task> findByAssignorId (Long userId);
    public List<Task> findByProjectId ( Long projectId );
}
