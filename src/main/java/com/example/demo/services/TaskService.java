package com.example.demo.services;

import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

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

    public ResponseEntity<Response> save (TaskRequest taskRequest, Principal principal ) throws UserNotFound, ProjectNotFound {
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

    public List<Task> findByAssigneeId (Long userId) throws TaskNotFound {
        return taskRepository.findByAssigneeId(userId).orElseThrow(TaskNotFound::new);
    }

    public List<Task> findByAssignorId (Long userId) throws TaskNotFound {
        return taskRepository.findByAssignorId(userId).orElseThrow(TaskNotFound::new);
    }

    public List<Task> findByProjectId (Long projectId) throws TaskNotFound {
        return taskRepository.findByProjectId(projectId).orElseThrow(TaskNotFound::new);
    }

    public Task findById (Long taskId ) throws TaskNotFound {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFound::new);
    }
}
