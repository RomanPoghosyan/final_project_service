package com.example.demo.services;

import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.dto.requests.NotificationStatusRequest;
import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.exceptions.NotificationNotFound;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.*;
import com.example.demo.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
        notification.setInvitationStatus(InvitationStatus.PENDING);
        save(notification);
        return notification;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }


    public List<NotificationResponse> getNotifications(String username) throws UserNotFound, ExecutionException, InterruptedException {
        User user = userService.findByUsername(username);
        List<Notification> notifications = notificationRepository.findAllByNotifiedToOrderByCreatedAtDesc(user);
        List<NotificationResponse> notificationResponses = convertToNotificationResponse(notifications);
        return notificationResponses;
    }

    public List<NotificationResponse> convertToNotificationResponse (List<Notification> notifications) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification : notifications) {
            String projectName = null;
            if ( notification.getProject() != null ) {
                projectName = notification.getProject().getName();
            }
            String taskTitle = null;
            if ( notification.getTask() != null ) {
                taskTitle = notification.getTask().getTitle();
            }
            NotificationStatus notificationStatus = notification.getStatus();
            NotificationType notificationType = notification.getType();
            String firstName = notification.getNotified_by().getFirst_name();
            String lastName = notification.getNotified_by().getLast_name();
            InvitationStatus invitationStatus = notification.getInvitationStatus();
            NotificationResponse notificationResponse = new NotificationResponse(notification.getId(), notificationStatus, notificationType, firstName, lastName,
                    projectName, taskTitle, invitationStatus);
            notificationResponses.add(notificationResponse);
        }
        return notificationResponses;
    }

    public List<NotificationResponse> getFiveNotifications () {
        List<Notification> notifications = notificationRepository.findTop5ByStatus(NotificationStatus.NOT_SEEN);
        if ( notifications.size() == 0 ) {
            return null;
        }
        List<NotificationResponse> notificationResponses = convertToNotificationResponse(notifications);
        return notificationResponses;
    }

    public Notification assignTask(AdditionRequest additionRequest, Principal principal) throws UserNotFound, TaskNotFound {
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
        save(notification);
        return notification;
    }

    public Notification findById ( Long id ) throws NotificationNotFound {
        return notificationRepository.findById(id).orElseThrow(NotificationNotFound::new);
    }

    public Notification setStatus (NotificationStatusRequest notificationStatusRequest) throws NotificationNotFound {
        Notification notification = findById(notificationStatusRequest.getNotificationId());
        if ( notificationStatusRequest.isSeen() ) {
            notification.setStatus(NotificationStatus.SEEN);
        } else {
            notification.setStatus(NotificationStatus.NOT_SEEN);
        }
        save(notification);
        return notification;
    }
}

