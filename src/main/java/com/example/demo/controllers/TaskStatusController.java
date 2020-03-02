package com.example.demo.controllers;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.dto.requests.TaskReorderRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskStatusNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.models.TaskStatus;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
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
        TaskStatus taskStatus = taskStatusService.add(addTaskStatusRequest, authentication);
        return new ResponseEntity<>(new OkResponse(new HashMap<Long, TaskStatus>(){{put(taskStatus.getId(), taskStatus);}}), HttpStatus.OK);
    }

    @PutMapping(value = "/task-reorder", consumes={"application/json"})
    public ResponseEntity<Response> updateColumnOrder(@RequestBody TaskReorderRequest taskReorderRequest, Authentication authentication) throws TaskStatusNotFound {
        return new ResponseEntity<>(new OkResponse(taskStatusService.updateTaskOrder(taskReorderRequest, authentication)), HttpStatus.OK);
    }
}
