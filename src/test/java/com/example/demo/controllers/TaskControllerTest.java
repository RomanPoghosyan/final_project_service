package com.example.demo.controllers;

import static org.mockito.Mockito.when;
import com.example.demo.dto.responses.DailyTasksResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.TaskInfoResponse;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskControllerTest {

    TaskController taskController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        taskController = new TaskController(taskService);
    }

    @Mock
    TaskService taskService;

    @Test
    public void testFindByProjectId() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        when(taskService.findByProjectId(1L)).thenReturn(taskList);
        ResponseEntity<Response> actual = taskController.findByProjectId(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), taskList);
    }

    @Test
    public void testFindByProjectIdFail() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        when(taskService.findByProjectId(1L)).thenReturn(taskList);
        ResponseEntity<Response> actual = taskController.findByProjectId(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), taskList);
    }

    @Test
    public void testFindDailyTasks() throws TaskNotFound {
        List<DailyTasksResponse> dailyTasksResponses = new ArrayList<>();
        LocalDateTime end = LocalDateTime.now().plusHours(12);
        LocalDateTime start = LocalDateTime.now();
        when(taskService.findByDueDateRange(start, end)).thenReturn(dailyTasksResponses);
        ResponseEntity<Response> actual = taskController.findDailyTasks();
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), dailyTasksResponses);
    }

    @Test
    public void testFindDailyTasksFail() throws TaskNotFound {
        List<DailyTasksResponse> dailyTasksResponses = new ArrayList<>();
        LocalDateTime end = LocalDateTime.now().plusHours(12);
        LocalDateTime start = LocalDateTime.now();
        when(taskService.findByDueDateRange(start, end)).thenReturn(dailyTasksResponses);
        ResponseEntity<Response> actual = taskController.findDailyTasks();
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), dailyTasksResponses);
    }

    @Test
    public void testGetCurrentTaskInfo() throws TaskNotFound {
        TaskInfoResponse taskInfoResponse = new TaskInfoResponse();
        when(taskService.getTaskInfo(1L)).thenReturn(taskInfoResponse);
        ResponseEntity<Response> actual = taskController.getCurrentTaskInfo(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), taskInfoResponse);
    }

    @Test
    public void testGetCurrentTaskInfoFail() throws TaskNotFound {
        TaskInfoResponse taskInfoResponse = new TaskInfoResponse();
        when(taskService.getTaskInfo(1L)).thenReturn(taskInfoResponse);
        ResponseEntity<Response> actual = taskController.getCurrentTaskInfo(1L);
        Assert.assertEquals(Objects.requireNonNull(actual.getBody()).getBody(), taskInfoResponse);
    }

}
