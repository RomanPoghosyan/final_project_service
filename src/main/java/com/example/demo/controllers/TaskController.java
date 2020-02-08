package com.example.demo.controllers;

import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.models.requests.TaskRequest;
import com.example.demo.models.responses.OkResponse;
import com.example.demo.models.responses.Response;
import com.example.demo.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Response> save (@RequestBody Task task) {
        taskService.save(task);
        return new ResponseEntity<>(new OkResponse(task), HttpStatus.CREATED);
    }

}
