package com.example.demo.services;
import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
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
    public void testFindById () {
        Task task = new Task();
        when(taskService.findById(1L)).thenReturn(Optional.of(task));
        Optional<Task> actual = taskService.findById(1L);
        Assert.assertEquals(actual.get(), task);
    }

    @DisplayName("Return empty task by given wrong id")
    @Test
    public void testFindByIdFail() {
        when(taskService.findById(2L)).thenReturn(Optional.empty());
        Optional<Task> actual = taskService.findById(2L);
        Assert.assertEquals(actual, Optional.empty());
    }

    @DisplayName("Add task to DB")
    @Test
    public void testSave () throws Exception {
        TaskRequest taskRequest = new TaskRequest();
        User user = new User();
        taskRequest.setAssignee_id(1L);
        taskRequest.setProject_id(1L);
        Project project = new Project();
        when (userService.findByUsername("Tigran")).thenReturn(user);
        when (userService.findById(1L)).thenReturn(user);
        when(projectService.findById(1L)).thenReturn(project);
        when (principal.getName()).thenReturn("Tigran");
        taskService.save(taskRequest, principal);
        verify(taskService).save(taskRequest, principal);
    }

    @DisplayName("Assignee or Assignor are not found in the taskRequest")
    @Test(expected = UserNotFound.class)
    public void testSaveWithUserNotFound () throws Exception {
        TaskService taskService = new TaskService(taskRepository, userService, projectService);
        TaskRequest taskRequest = new TaskRequest();
        when (principal.getName()).thenReturn("Tigran");
        taskService.save(taskRequest, principal);
        verify(taskService).save(taskRequest, principal);
    }

    @DisplayName("Project is not found in the taskRequest")
    @Test(expected = ProjectNotFound.class)
    public void testSaveWithProjectNotFound () throws Exception {
        TaskService taskService = new TaskService(taskRepository, userService, projectService);
        TaskRequest taskRequest = new TaskRequest();
        User user = new User();
        when (userService.findByUsername("Tigran")).thenReturn(user);
        when (principal.getName()).thenReturn("Tigran");
        taskService.save(taskRequest, principal);
        verify(taskService).save(taskRequest, principal);
    }
}
