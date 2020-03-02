package com.example.demo.services;

import com.example.demo.dto.requests.*;
import com.example.demo.dto.responses.FbNotificationResponse;
import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.exceptions.*;
import com.example.demo.models.*;
import com.example.demo.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private ProjectService projectService;
    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private ProjectUserRoleLinkService projectUserRoleLinkService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProjectService projectService,
                               UserService userService, TaskService taskService, RoleService roleService,
                               ProjectUserRoleLinkService projectUserRoleLinkService) {
        this.notificationRepository = notificationRepository;
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.projectUserRoleLinkService = projectUserRoleLinkService;
    }

    public Notification inviteToProject(InviteRequest invitedRequest, Principal principal) throws ProjectNotFound, UserNotFound, IOException {
        User inviter = userService.findByUsername(principal.getName());
        User invited = userService.findByUsername(invitedRequest.getUsername());
        Notification notification = new Notification();
        notification.setType(NotificationType.INVITATION);
        notification.setStatus(NotificationStatus.NOT_SEEN);
        notification.setNotifiedTo(invited);
        notification.setNotifiedBy(inviter);
        notification.setRoleId(invitedRequest.getRoleId());
        notification.setProject(projectService.findById(invitedRequest.getProjectId()));
        notification.setInvitationStatus(InvitationStatus.PENDING);
        save(notification);
        firebaseMessagingService.sendInvitationMessage(notification);
        return notification;
    }

    public Notification assignTask(AssignTaskRequest assignTaskRequest, Principal principal) throws ProjectNotFound, UserNotFound, IOException, TaskNotFound {
        User assignor = userService.findByUsername(principal.getName());
        User assignee = userService.findById(assignTaskRequest.getAssigneeId());
        Notification notification = new Notification();
        notification.setType(NotificationType.ASSIGNING);
        notification.setStatus(NotificationStatus.NOT_SEEN);
        notification.setNotifiedTo(assignee);
        notification.setNotifiedBy(assignor);
        notification.setTask(taskService.findById(assignTaskRequest.getTaskId()));
        notification.setProject(projectService.findById(assignTaskRequest.getProjectId()));
        save(notification);
        firebaseMessagingService.sendAssignmentNotification(notification);
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
                    notification.getType(), notification.getNotifiedBy().getFirst_name(), notification.getNotifiedBy().getLast_name(),
                    notification.getProject().getName(), notification.getTask() == null ? null : notification.getTask()
                    .getTitle(), notification.getInvitationStatus());
            notificationResponses.add(notificationResponse);
        });
        return notificationResponses;
    }

    public List<NotificationResponse> getFiveNotifications (String username) throws UserNotFound {
        User notified = userService.findByUsername(username);
        List<Notification> notifications = notificationRepository.findTop5ByStatusAndNotifiedTo(NotificationStatus.NOT_SEEN, notified);
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
        notification.setNotifiedTo(invited);
        notification.setNotifiedBy(inviter);
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

    public Notification replyToInvitation (ReplyToInvitationRequest replyToInvitationRequest) throws NotificationNotFound, RoleNotFound {
        Notification notification = findById(replyToInvitationRequest.getNotificationId());
        if ( replyToInvitationRequest.isAccepted() ) {
            notification.setInvitationStatus(InvitationStatus.ACCEPTED);
            ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink();
            projectUserRoleLink.setUser(notification.getNotifiedTo());
            projectUserRoleLink.setProject(notification.getProject());
            Role role = roleService.findById(notification.getRoleId());
            projectUserRoleLink.setRole(role);
            projectUserRoleLinkService.save(projectUserRoleLink);
        } else {
            notification.setInvitationStatus(InvitationStatus.REJECTED);
        }
        notification.setStatus(NotificationStatus.SEEN);

        save(notification);
        return notification;
    }
}

