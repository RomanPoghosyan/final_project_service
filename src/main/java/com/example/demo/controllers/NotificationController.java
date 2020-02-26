package com.example.demo.controllers;

import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.dto.requests.NotificationStatusRequest;
import com.example.demo.dto.requests.ReplyToInvitationRequest;
import com.example.demo.dto.responses.NotificationResponse;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.dto.responses.Response;
import com.example.demo.exceptions.*;
import com.example.demo.models.Notification;
import com.example.demo.models.NotificationStatus;
import com.example.demo.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping ("/invite")
    public ResponseEntity<Response> inviteToProject (@RequestBody InviteRequest inviteRequest, Principal principal ) throws ProjectNotFound, UserNotFound {
        Notification notification = notificationService.inviteToProject(inviteRequest, principal);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getAllNotifications ( Principal principal ) throws UserNotFound, ExecutionException, InterruptedException {
        List<NotificationResponse> notifications = notificationService.getNotifications(principal.getName());
        return new ResponseEntity<>(new OkResponse(notifications), HttpStatus.OK);
    }

    @PostMapping("/assign-task")
    public ResponseEntity<Response> assignTask (@RequestBody AdditionRequest additionRequest, Principal principal) throws UserNotFound, TaskNotFound {
        Notification notification = notificationService.assignTask(additionRequest, principal);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.CREATED);
    }

    @PutMapping("/set-status")
    public ResponseEntity<Response> setStatus (@RequestBody NotificationStatusRequest notificationStatusRequest) throws NotificationNotFound {
        Notification notification = notificationService.setStatus(notificationStatusRequest);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.OK);
    }

    @PutMapping("/reply")
    public ResponseEntity<Response> replyToInvitation (@RequestBody ReplyToInvitationRequest replyToInvitationRequest) throws NotificationNotFound, RoleNotFound {
        Notification notification = notificationService.replyToInvitation(replyToInvitationRequest);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.OK);
    }

    @GetMapping("/last-notifications")
    public ResponseEntity<Response> lastNotifications (Principal principal) throws UserNotFound {
        return new ResponseEntity<>(new OkResponse(notificationService.getFiveNotifications(principal.getName())), HttpStatus.OK);
    }
}
