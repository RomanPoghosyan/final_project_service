package com.example.demo.controllers;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TaskStatusService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.verify;

public class TaskStatusControllerTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    TaskStatusService taskStatusService;

    @Mock
    ProjectService projectService;

    @Mock
    AddTaskStatusRequest addTaskStatusRequest;

    @Mock
    Authentication authentication;

    @Test
    public void addTaskStatusTest() throws UserNotFound, ProjectNotFound {
        TaskStatusController taskStatusController = new TaskStatusController(taskStatusService, projectService);
        taskStatusController.addTaskStatus(addTaskStatusRequest, authentication);
        verify(taskStatusService).add(addTaskStatusRequest,authentication);
    }
}
