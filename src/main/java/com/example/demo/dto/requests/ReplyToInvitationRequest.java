package com.example.demo.dto.requests;

public class ReplyToInvitationRequest {
    private Long notificationId;

    private boolean isAccepted;

    public ReplyToInvitationRequest(Long notificationId, boolean isAccepted) {
        this.notificationId = notificationId;
        this.isAccepted = isAccepted;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
