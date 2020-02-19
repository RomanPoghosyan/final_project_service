package com.example.demo.services;

import com.example.demo.controllers.Authentication;
import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.*;
import com.example.demo.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class NotificationService {
    NotificationRepository notificationRepository;
    ProjectService projectService;
    UserService userService;
    TaskService taskService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProjectService projectService, UserService userService, TaskService taskService) {
        this.notificationRepository = notificationRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    public Notification inviteToProject(InviteRequest invitedRequest, Principal principal) throws ProjectNotFound, UserNotFound {
        User inviter = userService.findByUsername(principal.getName());
        User invited = userService.findById(invitedRequest.getInvitedId());
        Notification notification = new Notification();
        notification.setType(NotificationType.INVITATION);
        notification.setStatus(NotificationStatus.NOT_SEEN);
        notification.setNotified_to(invited);
        notification.setNotified_by(inviter);
        notification.setProjectId(projectService.findById(invitedRequest.getProjectId()));
        return notification;
    }

    public Notification save ( Notification notification ) {
        return notificationRepository.save(notification);
    }


    public List<Notification> getNotifications ( String username ) throws UserNotFound, ExecutionException, InterruptedException {
            User user = userService.findByUsername(username);
            List<Notification> notifications = user.getNotifications_by();
            System.out.println(notifications);
            return notifications;
    }

    public Notification addTask (AdditionRequest additionRequest, Principal principal) throws UserNotFound, TaskNotFound {
        User inviter = userService.findByUsername(principal.getName());
        User invited = userService.findById(additionRequest.getUserId());
        Task task = taskService.findById(additionRequest.getTaskId());
        Notification notification = new Notification();
        notification.setType(NotificationType.ASSIGNING);
        notification.setStatus(NotificationStatus.NOT_SEEN);
        notification.setNotified_to(invited);
        notification.setNotified_by(inviter);
        notification.setTask(task);
        notification.setProject(task.getProject());
        return notification;
    }
}
