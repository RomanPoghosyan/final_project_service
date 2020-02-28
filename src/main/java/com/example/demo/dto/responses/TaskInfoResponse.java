package com.example.demo.dto.responses;

import com.example.demo.models.Comment;
import com.example.demo.models.MicroTask;

import java.util.List;

public class TaskInfoResponse {

    private Long id;

    private String title;

    private String description;

    private Long assigneeId;

    private Long assignorId;

    private List<MicroTask> microTasks;

    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getAssignorId() {
        return assignorId;
    }

    public void setAssignorId(Long assignorId) {
        this.assignorId = assignorId;
    }

    public List<MicroTask> getMicroTasks() {
        return microTasks;
    }

    public void setMicroTasks(List<MicroTask> microTasks) {
        this.microTasks = microTasks;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
