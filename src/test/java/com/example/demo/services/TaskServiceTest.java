package com.example.demo.services;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repos.TaskRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest {

    @Before
    public void before() { MockitoAnnotations.initMocks(this); }

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserService userService;

    @Mock
    ProjectService projectService;

    @Mock
    Principal principal;

    @Mock
    TaskService taskService;

    @DisplayName("Return task by given id")
    @Test
    public void testFindById () throws TaskNotFound {
        TaskService taskService = new TaskService(taskRepository, userService, projectService);
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task actual = taskService.findById(1L);
        Assert.assertEquals(actual, task);
    }

    @Test(expected = TaskNotFound.class)
    public void testFindByIdFail() throws TaskNotFound {
        TaskService taskService = new TaskService(taskRepository, userService, projectService);
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());
        taskService.findById(2L);
    }
}
