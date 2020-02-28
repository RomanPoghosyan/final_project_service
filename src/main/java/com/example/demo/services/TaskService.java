package com.example.demo.services;

import com.example.demo.dto.requests.TaskRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.dto.responses.TaskInfoResponse;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.exceptions.TaskStatusNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.TaskStatus;
import com.example.demo.models.User;
import com.example.demo.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserService userService;
    private ProjectService projectService;
    private TaskStatusService taskStatusService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService, TaskStatusService taskStatusService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.taskStatusService = taskStatusService;
    }

    public Task save (TaskRequest taskRequest, Principal principal ) throws UserNotFound, ProjectNotFound, TaskStatusNotFound {
        User user = userService.findByUsername(principal.getName());
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        TaskStatus taskStatus = taskStatusService.findById( taskRequest.getTask_status_id() );
        task.setTask_status(taskStatus);

        task.setAssignor(user);
        Project project = projectService.findById(taskRequest.getProject_id());
        if(taskRequest.getAssignee_id() != null) {
            User assignee = userService.findById(taskRequest.getAssignee_id());
            task.setAssignee(assignee);
        }else{
            task.setAssignee(null);
        }
        task.setProject(project);
        taskRepository.save(task);

        List<Long> taskIds = taskStatus.getTaskIds();
        taskIds.add(task.getId());
        taskStatus.setTaskIds(taskIds);
        taskStatusService.save(taskStatus);
        return task;
    }

    public List<Task> findByAssigneeId (Long userId) throws TaskNotFound {
        return taskRepository.findByAssigneeId(userId).orElseThrow(TaskNotFound::new);
    }

    public List<Task> findByAssignorId (Long userId) throws TaskNotFound {
        return taskRepository.findByAssignorId(userId).orElseThrow(TaskNotFound::new);
    }

    public List<Task> findByProjectId (Long projectId) throws TaskNotFound {
        return taskRepository.findByProjectId(projectId).orElseThrow(TaskNotFound::new);
    }

    public Task findById (Long taskId ) throws TaskNotFound {
        return taskRepository.findById(taskId).orElseThrow(TaskNotFound::new);
    }

    public TaskInfoResponse getTaskInfo (Long taskId ) throws TaskNotFound {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFound::new);

        TaskInfoResponse taskInfoResponse = new TaskInfoResponse();
        taskInfoResponse.setId(task.getId());
        taskInfoResponse.setTitle(task.getTitle());
        taskInfoResponse.setDescription(task.getDescription());
        if(task.getAssignee() != null) {
            taskInfoResponse.setAssigneeId(task.getAssignee().getId());
        } else {
            taskInfoResponse.setAssigneeId(null);
        }
        taskInfoResponse.setAssignorId(task.getAssignor().getId());
        taskInfoResponse.setMicroTasks(task.getMicro_tasks());
        taskInfoResponse.setComments(task.getComments());
        return taskInfoResponse;
    }
}
