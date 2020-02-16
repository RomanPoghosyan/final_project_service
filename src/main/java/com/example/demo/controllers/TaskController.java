package com.example.demo.controllers;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.models.Task;
import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Response> save (@RequestBody TaskRequest taskRequest, Principal principal) throws Exception {
        taskService.save(taskRequest, principal);
        return new ResponseEntity<>(new OkResponse(taskRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> findByProjectId ( @RequestBody Long projectId ) throws TaskNotFound {
        List<Task> tasks = taskService.findByProjectId(projectId);
        return new ResponseEntity<>(new OkResponse(tasks), HttpStatus.OK);
    }
}
