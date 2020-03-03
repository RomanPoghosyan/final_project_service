package com.example.demo.services;

import com.example.demo.dto.responses.DailyTasksResponse;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.models.Task;
import com.example.demo.repos.TaskRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class TaskServiceTest {

    TaskService taskService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskService(taskRepository, userService, projectService, taskStatusService, notificationService);
    }

    @Mock
    TaskRepository taskRepository;

    @Mock
    NotificationService notificationService;

    @Mock
    UserService userService;

    @Mock
    ProjectService projectService;

    @Mock
    private TaskStatusService taskStatusService;

    @Test
    public void testFindById() throws TaskNotFound {
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task actual = taskService.findById(1L);
        Assert.assertEquals(actual, task);
    }

    @Test(expected = TaskNotFound.class)
    public void testFindByIdFail() throws TaskNotFound {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());
        taskService.findById(2L);
    }

    @Test
    public void testFindByAssigneeId() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        when(taskRepository.findByAssigneeId(1L)).thenReturn(Optional.of(taskList));
        List<Task> actual = taskService.findByAssigneeId(1L);
        Assert.assertEquals(actual, taskList);
    }

    @Test
    public void testFindByAssignorId() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        when(taskRepository.findByAssignorId(1L)).thenReturn(Optional.of(taskList));
        List<Task> actual = taskService.findByAssignorId(1L);
        Assert.assertEquals(actual, taskList);
    }

    @Test
    public void testFindByProjectId() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        when(taskRepository.findByProjectId(1L)).thenReturn(Optional.of(taskList));
        List<Task> actual = taskService.findByProjectId(1L);
        Assert.assertEquals(actual, taskList);
    }

    @Test
    public void testFindByDueDateRange() throws TaskNotFound {
        List<Task> taskList = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(12);
        when(taskRepository.findTaskByDueDateGreaterThanAndDueDateLessThan(start, end)).thenReturn(Optional.of(taskList));
        List<DailyTasksResponse> dailyTasksResponseList = taskService.mapToDailyTasksResponse(taskList);
        List<DailyTasksResponse> actual = taskService.findByDueDateRange(start, end);
        Assert.assertEquals(actual, dailyTasksResponseList);
    }

}
