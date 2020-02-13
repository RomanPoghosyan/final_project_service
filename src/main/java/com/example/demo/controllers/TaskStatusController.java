package com.example.demo.controllers;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/statuses")
public class TaskStatusController {
    private TaskStatusService taskStatusService;
    private ProjectService projectService;

    @Autowired
    public TaskStatusController(TaskStatusService taskStatusService, ProjectService projectService){
        this.taskStatusService = taskStatusService;
        this.projectService = projectService;
    }

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<Response> addTaskStatus(@RequestBody AddTaskStatusRequest addTaskStatusRequest, Authentication authentication) throws UserNotFound, ProjectNotFound {
        return new ResponseEntity<>(new OkResponse(taskStatusService.add(addTaskStatusRequest, authentication)), HttpStatus.OK);
    }
}
