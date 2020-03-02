package com.example.demo.services;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import org.springframework.security.core.Authentication;
import com.example.demo.models.TaskStatus;
import com.example.demo.repos.TaskStatusRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskStatusServiceTest {
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private TaskStatusRepository taskStatusRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Mock
    AddTaskStatusRequest addTaskStatusRequest;

    @Mock
    FirebaseMessagingService firebaseMessagingService;

    @Mock
    Authentication authentication;

    @Test//????? xia qcum nullpointer
    public void testTaskStatusServiceAdd() throws UserNotFound, ProjectNotFound {
        TaskStatusService taskStatusService = new TaskStatusService(userService, projectService,
                taskStatusRepository, firebaseMessagingService);
        Project project = new Project();
        when(authentication.getName()).thenReturn("John");
        when(addTaskStatusRequest.getProjectId()).thenReturn(1L);
        when(addTaskStatusRequest.getName()).thenReturn("John");
        projectService.findById(addTaskStatusRequest.getProjectId());
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTitle(addTaskStatusRequest.getName());
        taskStatus.setProject(project);
        when(taskStatusRepository.save(taskStatus)).thenReturn(taskStatus);
        taskStatusService.add(addTaskStatusRequest, authentication);
        verify(taskStatusRepository).save(taskStatus);
    }
}
