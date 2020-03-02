package com.example.demo.services;

import com.example.demo.dto.responses.FbNotificationResponse;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirebaseMessagingService {
    private RestTemplate restTemplate;

    private static final String API_KEY = "AAAAoKWp3VM:APA91bFMjn87fPEUsUCtxJ4Loz0SsQ3GUb-1Al34ZoxaOP0x18ivCOhOTQhEhbg_YyYO790IOkzxwokjQuVs6AsbXuqD6guURCjEr_Qz8aAylndknxzMoyBnZWks7GR50IUEb0X5fT7L";

    private static final String URL = "https://fcm.googleapis.com/fcm/send";

    private static final MediaType APPLICATION_TYPE = MediaType.APPLICATION_JSON;

    private static final String CLICK_ACTION_URL = "http://localhost:3000/";


    private HttpHeaders headers;

    @Autowired
    public FirebaseMessagingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.headers = new HttpHeaders();
        headers.set("Authorization", "key=" + API_KEY);
    }


    void notifyProjectUsers(List<ProjectUserRoleLink> projectUserRoleLinks,
                            String username, NotificationType notificationType) {
        for (ProjectUserRoleLink projectUserRoleLink : projectUserRoleLinks) {
            User user = projectUserRoleLink.getUser();
            if (!user.getUsername().equals(username)) {
                if (notificationType == NotificationType.TASK_REORDER) {
                    taskReorder(user);
                } else if ( notificationType == NotificationType.BOARD_REORDER ) {
                    boardReorder(user);
                } else if ( notificationType == NotificationType.ADD_COLUMN ) {
                    addColumn(user);
                } else if ( notificationType == NotificationType.ADD_TASK ) {
                    addTask(user);
                } else if ( notificationType == NotificationType.ACCEPT_INVITATION ) {
                    acceptInvitation(user, username);
                }
            }
        }
    }

    void sendInvitationMessage(Notification notification) {
        String notificationBody = notification.getNotifiedBy().getUsername() + " has invited you to the " + notification.getProject().getName() + "project!";
        sendNotification(notification.getNotifiedTo().getFb_token(), notificationBody, NotificationType.INVITATION);
    }

    private void acceptInvitation ( User user, String username ) {
        String notificationBody = username + " has accepted project invitation!";
        sendNotification(user.getFb_token(), notificationBody, NotificationType.ACCEPT_INVITATION);
    }

    private void addColumn(User user) {
        String notificationBody = "Added column to the project!";
        sendNotification(user.getFb_token(), notificationBody, NotificationType.ADD_COLUMN);
    }

    private void addTask(User user) {
        String notificationBody = "Added task!";
        sendNotification(user.getFb_token(), notificationBody, NotificationType.ADD_TASK);
    }

    private void taskReorder(User user) {
        String notificationBody = "Tasks are reordered!!!";
        sendNotification(user.getFb_token(), notificationBody, NotificationType.TASK_REORDER);
    }

    private void boardReorder(User user) {
        String notificationBody = "Boards are reordered!";
        sendNotification(user.getFb_token(), notificationBody, NotificationType.BOARD_REORDER);
    }

    void sendAssignmentNotification(Notification notification) {
        String notificationBody = notification.getNotifiedBy().getUsername() + " has assigned you the task " + notification.getTask().getTitle() + " in project " + notification.getProject().getName();
        sendNotification(notification.getNotifiedTo().getFb_token(), notificationBody, NotificationType.ASSIGNING);
    }

    private void sendNotification(String fbToken, String notificationBody, NotificationType notificationType) {
        Map<String, Object> map = new HashMap<>();
        map.put("to", fbToken);
        map.put("notification", new FbNotificationResponse(
                CLICK_ACTION_URL, notificationBody, notificationType
        ));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        this.restTemplate.postForEntity(URL, entity, Object.class);
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getURL() {
        return URL;
    }

    public static MediaType getApplicationType() {
        return APPLICATION_TYPE;
    }

    public static String getClickActionUrl() {
        return CLICK_ACTION_URL;
    }


    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}