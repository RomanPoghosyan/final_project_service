package com.example.demo.services;

import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.exceptions.NotificationNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Notification;
import com.example.demo.models.User;
import com.example.demo.repos.NotificationRepository;

import static org.mockito.Mockito.verify;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.when;

public class NotificationServiceTest {
    NotificationService notificationService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        notificationService = new NotificationService(notificationRepository, projectService,
                userService, taskService, roleService, projectUserRoleLinkService, firebaseMessagingService);
    }

    @Mock
    UserService userService;

    @Mock
    ProjectService projectService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    TaskService taskService;

    @Mock
    RoleService roleService;

    @Mock
    ProjectUserRoleLinkService projectUserRoleLinkService;

    @Mock
    FirebaseMessagingService firebaseMessagingService;

    @Test
    public void testSave() {
        Notification notification = new Notification();
        notificationService.save(notification);
        verify(notificationRepository).save(notification);
    }

    @Test
    public void testGetNotifications() throws UserNotFound, ExecutionException, InterruptedException {
        User user = new User();
        when(userService.findByUsername("John")).thenReturn(user);
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findAllByNotifiedToOrderByCreatedAtDesc(user)).thenReturn(notifications);
        List<NotificationResponse> notificationResponses = notificationService.convertToNotificationResponse(notifications);
        List<NotificationResponse> actual = notificationService.getNotifications("John");
        Assert.assertEquals(actual, notificationResponses);
    }

    @Test
    public void testConvertToNotificationResponse() {
        List<Notification> notificationList = new ArrayList();
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        notificationList.forEach(notification -> {
            NotificationResponse notificationResponse = new NotificationResponse(notification.getId(), notification.getStatus(),
                    notification.getType(), notification.getNotifiedBy().getFirst_name(), notification.getNotifiedBy().getLast_name(),
                    notification.getProject().getName(), notification.getTask() == null ? null : notification.getTask()
                    .getTitle(), notification.getInvitationStatus());
            notificationResponses.add(notificationResponse);
        });
        List<NotificationResponse> actual = notificationService.convertToNotificationResponse(notificationList);
        Assert.assertEquals(actual, notificationResponses);
    }

    @Test
    public void testFindById() throws NotificationNotFound {
        Notification notification = new Notification();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        Notification actual = notificationService.findById(1L);
        Assert.assertEquals(actual, notification);
    }

}
