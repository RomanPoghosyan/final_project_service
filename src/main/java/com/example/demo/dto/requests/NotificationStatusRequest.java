package com.example.demo.dto.requests;

public class NotificationStatusRequest {
    private Long notificationId;

    private boolean isSeen;

    public NotificationStatusRequest(Long notificationId, boolean isSeen) {
        this.notificationId = notificationId;
        this.isSeen = isSeen;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
