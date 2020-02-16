package com.example.demo.services;

import com.example.demo.dto.requests.AddTaskStatusRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.TaskStatus;
import com.example.demo.models.User;
import com.example.demo.repos.ProjectRepository;
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

    @Autowired
    public TaskStatusService(UserService userService, ProjectService projectService, TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
        this.userService = userService;
        this.projectService = projectService;
    }


    public TaskStatus add(AddTaskStatusRequest addTaskStatusRequest, Authentication authentication) throws UserNotFound, ProjectNotFound {
        User user = userService.findByUsername(authentication.getName());
        Project project = projectService.findById(addTaskStatusRequest.getProjectId());
        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setName(addTaskStatusRequest.getName());
        taskStatus.setProject(project);
        TaskStatus saved = taskStatusRepository.save(taskStatus);

        try{
            Long[] taskStatusesOrder = project.getTaskStatusesOrder();
            List<Long> newTaskStatusesOrder = Arrays.asList(taskStatusesOrder);
            newTaskStatusesOrder.add(saved.getId());
            project.setTaskStatusesOrder((Long[]) newTaskStatusesOrder.toArray());
        } catch (Exception e){
            project.setTaskStatusesOrder(new Long[]{saved.getId()});
        }
        projectService.save(project);
        return saved;
    }
}

