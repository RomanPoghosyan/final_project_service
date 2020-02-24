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
//        List<Notification> list = notificationRepository.findAllByOrderByCreatedAtDesc();
//        System.out.println(list);
        User user = userService.findByUsername(username);
        List<Notification> notifications = notificationRepository.findAllByNotifiedToOrderByCreatedAtDesc(user);
        List<NotificationResponse> notificationResponses = convertToNotificationResponse(notifications);
        return notificationResponses;
    }

    public List<NotificationResponse> convertToNotificationResponse (List<Notification> notifications) {
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        notifications.forEach(notification -> {
            NotificationResponse notificationResponse = new NotificationResponse(notification.getId(), notification.getStatus(),
                    notification.getType(), notification.getNotified_by().getFirst_name(), notification.getNotified_by().getLast_name(),
                    notification.getProject().getName(), notification.getTask() == null ? null : notification.getTask()
                    .getTitle(), notification.getInvitationStatus());
            notificationResponses.add(notificationResponse);
        });
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

