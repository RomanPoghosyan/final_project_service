package com.example.demo.controllers;

import com.example.demo.dto.requests.TaskReorderRequest;
import com.example.demo.exceptions.TaskStatusNotFound;
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
    Authentication authentication;

    @Mock
    TaskReorderRequest taskReorderRequest;

    @Test
    public void updateColumnOrderTest() throws TaskStatusNotFound {
        TaskStatusController taskStatusController = new TaskStatusController(taskStatusService, projectService);
        taskStatusController.updateColumnOrder(taskReorderRequest, authentication);
        verify(taskStatusService).updateTaskOrder(taskReorderRequest, authentication);
    }
}
