package com.example.demo.dto.responses;

import com.example.demo.models.MicroTask;

import java.util.List;

public class TaskMiniInfoResponse {
    private Long id;
    private String title;
    private Long assigneeId;
    private List<MicroTask> microTasks;

    public TaskMiniInfoResponse(Long id, String title, Long assigneeId, List<MicroTask> microTasks) {
        this.id = id;
        this.title = title;
        this.assigneeId = assigneeId;
        this.microTasks = microTasks;
    }

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

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public List<MicroTask> getMicroTasks() {
        return microTasks;
    }

    public void setMicroTasks(List<MicroTask> microTasks) {
        this.microTasks = microTasks;
    }
}
