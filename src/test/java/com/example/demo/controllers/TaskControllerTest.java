package com.example.demo.controllers;

import static org.mockito.Mockito.verify;

import com.example.demo.services.TaskService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;
import java.security.Principal;

public class TaskControllerTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    TaskService taskService;

    @Mock
    Principal principal;

    @Test
    public void addTask() throws Exception {
//        TaskController taskController = new TaskController(taskService);
//        TaskRequest taskRequest = new TaskRequest();
//        taskController.save(taskRequest, principal);
//        verify(taskService).save(taskRequest, principal);
    }
}
