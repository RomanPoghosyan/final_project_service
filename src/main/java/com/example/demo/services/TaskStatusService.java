package com.example.demo.services;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.dto.requests.TaskReorderRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskStatusNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.*;
import com.example.demo.repos.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class TaskStatusService {

    private TaskStatusRepository taskStatusRepository;
    private UserService userService;
    private ProjectService projectService;
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public TaskStatusService(UserService userService, ProjectService projectService,
                             TaskStatusRepository taskStatusRepository, FirebaseMessagingService firebaseMessagingService) {
        this.taskStatusRepository = taskStatusRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.firebaseMessagingService = firebaseMessagingService;

    }

    public TaskStatus add(AddTaskStatusRequest addTaskStatusRequest, Authentication authentication) throws UserNotFound, ProjectNotFound {
        User user = userService.findByUsername(authentication.getName());
        Project project = projectService.findById(addTaskStatusRequest.getProjectId());
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTitle(addTaskStatusRequest.getName());
        taskStatus.setProject(project);
        TaskStatus saved = taskStatusRepository.save(taskStatus);
        try {
            List<Long> taskStatusesOrder = project.getTaskStatusesOrder();
            taskStatusesOrder.add(saved.getId());
            project.setTaskStatusesOrder(taskStatusesOrder);
        } catch (Exception e) {
            project.setTaskStatusesOrder(Arrays.asList(saved.getId()));
        }
        projectService.save(project);
        List<ProjectUserRoleLink> projectUserRoleLinks = project.getProjectUserRoleLinks();
        firebaseMessagingService.notifyProjectUsers(projectUserRoleLinks,
                authentication.getName(), NotificationType.ADD_COLUMN);
        return saved;
    }

    public TaskStatus findById(Long taskStatusId) throws TaskStatusNotFound {
        return taskStatusRepository.findById(taskStatusId).orElseThrow(TaskStatusNotFound::new);
    }

    public List<Long> updateTaskOrder(TaskReorderRequest taskReorderRequest, Authentication authentication) throws TaskStatusNotFound {
        TaskStatus taskStatus = taskStatusRepository.findById(taskReorderRequest.getColumnId()).orElseThrow(TaskStatusNotFound::new);
        taskStatus.setTaskIds(taskReorderRequest.getTaskIds());
        taskStatusRepository.save(taskStatus);
        List<ProjectUserRoleLink> projectUserRoleLinks = taskStatus.getProject().getProjectUserRoleLinks();
        firebaseMessagingService.notifyProjectUsers(projectUserRoleLinks,
                authentication.getName(), NotificationType.TASK_REORDER);
        return taskReorderRequest.getTaskIds();
    }

    public TaskStatus save(TaskStatus taskStatus) {
        return taskStatusRepository.save(taskStatus);
    }
}

