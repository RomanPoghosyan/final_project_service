package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @JsonIgnore
    @ManyToOne
    private User notified_by;

    @JsonIgnore
    @ManyToOne
    private User notified_to;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Task task;

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

    public User getNotified_by() {
        return notified_by;
    }

    public void setNotified_by(User notified_by) {
        this.notified_by = notified_by;
    }

    public User getNotified_to() {
        return notified_to;
    }

    public void setNotified_to(User notified_to) {
        this.notified_to = notified_to;
    }

    public Project getProject() {
        return project;
    }

    public void setProjectId(Project project) {
        this.project = project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
