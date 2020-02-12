package com.example.demo.models.requests;

import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.TaskStatus;
import com.example.demo.models.User;

public class TaskRequest {
    private String title;

    private String description;

    //TODO to implement Task Status for get id
    private TaskStatus task_status_id;

    private Long project_id;

    private Long assignee_id;

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

    public TaskStatus getTask_status_id() {
        return task_status_id;
    }

    public void setTask_status_id(TaskStatus task_status_id) {
        this.task_status_id = task_status_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(Long assignee_id) {
        this.assignee_id = assignee_id;
    }
}
