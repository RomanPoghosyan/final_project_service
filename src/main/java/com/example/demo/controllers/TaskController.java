package com.example.demo.controllers;

import com.example.demo.dto.requests.ChangeTaskDescriptionRequest;
import com.example.demo.dto.requests.ChangeTaskTitleRequest;
import com.example.demo.dto.responses.DailyTasksResponse;
import com.example.demo.dto.responses.TaskInfoResponse;
import com.example.demo.dto.responses.TaskMiniInfoResponse;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.models.Task;
import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.models.TaskStatus;
import com.example.demo.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
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
        Task task = taskService.save(taskRequest, principal);
        Long assignee_id = task.getAssignee() != null ? task.getAssignee().getId() : null;
        TaskMiniInfoResponse taskMiniInfoResponse = new TaskMiniInfoResponse(task.getId(), task.getTitle(), assignee_id, task.getMicro_tasks());

        return new ResponseEntity<>(new OkResponse(new HashMap<Long, TaskMiniInfoResponse>(){{put(taskMiniInfoResponse.getId(), taskMiniInfoResponse);}}), HttpStatus.CREATED);
    }

    @GetMapping("/byProject/{projectId}")
    public ResponseEntity<Response> findByProjectId(@PathVariable Long projectId) throws TaskNotFound {
        List<Task> tasks = taskService.findByProjectId(projectId);
        return new ResponseEntity<>(new OkResponse(tasks), HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<Response> findDailyTasks() throws TaskNotFound {
        LocalDateTime end = LocalDateTime.now().plusHours(12);
        LocalDateTime start = LocalDateTime.now();
        List<DailyTasksResponse> tasks = taskService.findByDueDateRange(start, end);
        return new ResponseEntity<>(new OkResponse(tasks), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Response> getCurrentTaskInfo(@PathVariable Long taskId) throws TaskNotFound {
        TaskInfoResponse task = taskService.getTaskInfo(taskId);
        return new ResponseEntity<>(new OkResponse(task), HttpStatus.OK);
    }

    @PutMapping("/title")
    public ResponseEntity<Response> changeTitle(@RequestBody ChangeTaskTitleRequest changeTaskTitleRequest) throws TaskNotFound {
        return new ResponseEntity<>(new OkResponse(taskService.changeTitle(changeTaskTitleRequest)), HttpStatus.OK);
    }

    @PutMapping("/description")
    public ResponseEntity<Response> changeDescription(@RequestBody ChangeTaskDescriptionRequest changeTaskDescriptionRequest) throws TaskNotFound {
        return new ResponseEntity<>(new OkResponse(taskService.changeDescription(changeTaskDescriptionRequest)), HttpStatus.OK);
    }
}
