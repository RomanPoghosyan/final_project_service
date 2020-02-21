package com.example.demo.dto.responses;

import com.example.demo.models.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class ProjectResponse {
    private Long id;
    private String name;
    private List<TaskMiniInfoResponse> tasks = new ArrayList<>();
    private List<TaskStatus> taskStatuses = new ArrayList<>();
    private List<Long> taskStatusesOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskMiniInfoResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskMiniInfoResponse> tasks) {
        this.tasks = tasks;
    }

    public List<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(List<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public List<Long> getTaskStatusesOrder() {
        return taskStatusesOrder;
    }

    public void setTaskStatusesOrder(List<Long> taskStatusesOrder) {
        this.taskStatusesOrder = taskStatusesOrder;
    }
}
