package com.example.demo.dto.responses;

import com.example.demo.models.Notification;
import com.example.demo.models.NotificationType;

public class FbNotificationResponse {
    private String title;

    private String click_action;

    private String body;

    private NotificationType notificationType;

    public FbNotificationResponse(String click_action, String body, NotificationType notificationType) {
        this.title = notificationType.toString();
        this.click_action = click_action;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
