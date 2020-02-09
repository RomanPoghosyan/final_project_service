package com.example.demo.services;

import com.example.demo.auth.CustomUser;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserService userService;
    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }



    public Task save (Task task, Principal principal ) {
        System.out.println(principal.getName());
        Optional<User> user = userService.findByUsername(principal.getName());
        task.setAssignor(user.get());
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

    public Optional<Task> findById (Long taskId ) { return taskRepository.findById(taskId); }
}
