package com.example.demo.services;

import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.dto.requests.NotificationStatusRequest;
import com.example.demo.dto.requests.ReplyToInvitationRequest;
import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.exceptions.*;
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

    public Notification inviteToProject(InviteRequest invitedRequest, Principal principal) throws ProjectNotFound, UserNotFound {
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

    private List<NotificationResponse> convertToNotificationResponse (List<Notification> notifications) {
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
            String firstName = notification.getNotifiedBy().getFirst_name();
            String lastName = notification.getNotifiedBy().getLast_name();
            InvitationStatus invitationStatus = notification.getInvitationStatus();
            NotificationResponse notificationResponse = new NotificationResponse(notification.getId(), notificationStatus, notificationType, firstName, lastName,
                    projectName, taskTitle, invitationStatus);
            notificationResponses.add(notificationResponse);
        }
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
        } else {
            notification.setInvitationStatus(InvitationStatus.REJECTED);
        }
        ProjectUserRoleLink projectUserRoleLink = new ProjectUserRoleLink();
        projectUserRoleLink.setUser(notification.getNotifiedTo());
        projectUserRoleLink.setProject(notification.getProject());
        Role role = roleService.findById(notification.getRoleId());
        projectUserRoleLink.setRole(role);
        projectUserRoleLinkService.save(projectUserRoleLink);

        save(notification);
        return notification;
    }
}

