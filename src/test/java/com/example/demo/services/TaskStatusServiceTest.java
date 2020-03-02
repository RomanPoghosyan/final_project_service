package com.example.demo.services;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.dto.requests.TaskReorderRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskStatusNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.security.core.Authentication;
import com.example.demo.models.TaskStatus;
import com.example.demo.repos.TaskStatusRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaskStatusServiceTest {

    TaskStatusService taskStatusService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        taskStatusService = new TaskStatusService(userService, projectService, taskStatusRepository);
    }

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Test
    public void testFindById() throws TaskStatusNotFound {
        TaskStatus taskStatus = new TaskStatus();
        when(taskStatusRepository.findById(1L)).thenReturn(Optional.of(taskStatus));
        TaskStatus actual = taskStatusService.findById(1L);
        Assert.assertEquals(taskStatus, actual);
    }
}
