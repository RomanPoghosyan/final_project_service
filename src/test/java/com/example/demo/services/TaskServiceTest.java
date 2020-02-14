package com.example.demo.services;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.models.Task;
import com.example.demo.repos.TaskRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
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
