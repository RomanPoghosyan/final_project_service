package com.example.demo.dto.responses;

import com.example.demo.models.InvitationStatus;
import com.example.demo.models.NotificationStatus;
import com.example.demo.models.NotificationType;

public class NotificationResponse {

    private Long id;

    private NotificationStatus status;

    private NotificationType type;

    private String notifiedByFirstName;

    private String notifiedByLastName;

    private String projectName;

    private String taskTitle;

    private InvitationStatus invitationStatus;

    public NotificationResponse(Long id, NotificationStatus status, NotificationType type, String notifiedByFirstName, String notifiedByLastName, String projectName, String taskTitle, InvitationStatus invitationStatus) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.notifiedByFirstName = notifiedByFirstName;
        this.notifiedByLastName = notifiedByLastName;
        this.projectName = projectName;
        this.taskTitle = taskTitle;
        this.invitationStatus = invitationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getNotifiedByFirstName() {
        return notifiedByFirstName;
    }

    public void setNotifiedByFirstName(String notifiedByFirstName) {
        this.notifiedByFirstName = notifiedByFirstName;
    }

    public String getNotifiedByLastName() {
        return notifiedByLastName;
    }

    public void setNotifiedByLastName(String notifiedByLastName) {
        this.notifiedByLastName = notifiedByLastName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }
}
