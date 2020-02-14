package com.example.demo.services;

import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserService userService;
    private ProjectService projectService;
    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }



    public ResponseEntity<?> save (TaskRequest taskRequest, Principal principal ) throws Exception {
        User user = userService.findByUsername(principal.getName());
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setTask_status(taskRequest.getTask_status_id());
        task.setAssignor(user);
        Project project = projectService.findById(taskRequest.getProject_id());
        User assignee = userService.findById(taskRequest.getAssignee_id());
        task.setProject(project);
        task.setAssignee(assignee);
        return new ResponseEntity<>(new OkResponse(taskRepository.save (task)), HttpStatus.CREATED);
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
