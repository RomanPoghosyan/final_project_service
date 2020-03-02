package com.example.demo.controllers;

import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.dto.requests.NotificationStatusRequest;
import com.example.demo.dto.requests.ReplyToInvitationRequest;
import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.*;
import com.example.demo.models.Notification;
import com.example.demo.services.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.when;

public class NotificationControllerTest {

    @Mock
    NotificationController notificationController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        notificationController = new NotificationController(notificationService);
    }

    @Mock
    InviteRequest inviteRequest;

    @Mock
    Principal principal;

    @Mock
    NotificationService notificationService;

    @Mock
    AdditionRequest additionRequest;

    @Mock
    NotificationStatusRequest notificationStatusRequest;

    @Mock
    ReplyToInvitationRequest replyToInvitationRequest;

    @Test
    public void inviteToProjectTest() throws IOException, UserNotFound, ProjectNotFound {
        Notification notification = new Notification();
        when(notificationService.inviteToProject(inviteRequest, principal)).thenReturn(notification);
        ResponseEntity<Response> responseEntity = notificationController.inviteToProject(inviteRequest, principal);
        Assert.assertEquals(responseEntity.getBody().getBody(), notification);
    }

    @Test
    public void getAllNotificationsTest() throws UserNotFound, ExecutionException, InterruptedException {
        NotificationController notificationController = new NotificationController(notificationService);
        List<NotificationResponse> responseList = new ArrayList<>();
        when(notificationService.getFiveNotifications(principal.getName())).thenReturn(responseList);
        ResponseEntity<Response> actual = notificationController.getAllNotifications(principal);
        Assert.assertEquals(actual.getBody().getBody(), responseList);
    }

    @Test
    public void assignTaskTest() throws UserNotFound, TaskNotFound {
        Notification notification = new Notification();
        when(notificationService.assignTask(additionRequest, principal)).thenReturn(notification);
        ResponseEntity<Response> responseEntity = notificationController.assignTask(additionRequest, principal);
        Assert.assertEquals(responseEntity.getBody().getBody(), notification);
    }

    @Test
    public void setStatusTest() throws NotificationNotFound {
        Notification notification = new Notification();
        when(notificationService.setStatus(notificationStatusRequest)).thenReturn(notification);
        ResponseEntity<Response> responseEntity = notificationController.setStatus(notificationStatusRequest);
        Assert.assertEquals(responseEntity.getBody().getBody(), notification);
    }

    @Test
    public void replyToInvitationTest() throws RoleNotFound, NotificationNotFound {
        Notification notification = notificationService.replyToInvitation(replyToInvitationRequest);
        ResponseEntity<Response> actual = notificationController.replyToInvitation(replyToInvitationRequest);
        Assert.assertEquals(actual.getBody().getBody(), notification);
    }

    @Test
    public void lastNotificationsTest() throws UserNotFound {
        List<NotificationResponse> notificationResponses = notificationService.getFiveNotifications(principal.getName());
        ResponseEntity<Response> responseEntity = notificationController.lastNotifications(principal);
        Assert.assertEquals(responseEntity.getBody().getBody(), notificationResponses);
    }
}
