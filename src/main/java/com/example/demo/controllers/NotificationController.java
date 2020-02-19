package com.example.demo.controllers;

import com.example.demo.dto.requests.AdditionRequest;
import com.example.demo.dto.requests.InviteRequest;
import com.example.demo.dto.responses.OkResponse;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.TaskNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.Notification;
import com.example.demo.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*", maxAge = 10000)
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping ("/invite")
    public ResponseEntity<?> inviteToProject (@RequestBody InviteRequest inviteRequest, Principal principal ) throws ProjectNotFound, UserNotFound {
        Notification notification = notificationService.inviteToProject(inviteRequest, principal);
        notificationService.save(notification);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks ( Principal principal ) throws UserNotFound, ExecutionException, InterruptedException {
        List<Notification> notifications = notificationService.getNotifications(principal.getName());
        return new ResponseEntity<>(notifications, HttpStatus.FOUND);
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask (@RequestBody AdditionRequest additionRequest, Principal principal) throws UserNotFound, TaskNotFound {
        Notification notification = notificationService.addTask(additionRequest, principal);
        notificationService.save(notification);
        return new ResponseEntity<>(new OkResponse(notification), HttpStatus.CREATED);
    }
}
