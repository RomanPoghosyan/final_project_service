package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save ( Task task ) {
        return taskRepository.save (task);
    }

    public List<Task> findByAssigneeId (Long userId) {
        return taskRepository.findByAssigneeId(userId);
    }

    public List<Task> findByAssignorId (Long userId) {
        return taskRepository.findByAssignorId(userId);
    }

    public List<Task> findByProjectId (Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
