package com.example.demo.models.requests;

import com.example.demo.models.Project;
import com.example.demo.models.TaskStatus;
import com.example.demo.models.User;

public class TaskRequest {
    private String title;

    private String description;

    private TaskStatus task_status;

    private Project project;

    private User assignor;

    private User assignee;

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

    public TaskStatus getTask_status() {
        return task_status;
    }

    public void setTask_status(TaskStatus task_status) {
        this.task_status = task_status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignor() {
        return assignor;
    }

    public void setAssignor(User assignor) {
        this.assignor = assignor;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
}
