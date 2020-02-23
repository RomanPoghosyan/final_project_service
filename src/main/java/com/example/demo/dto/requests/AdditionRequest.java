package com.example.demo.dto.requests;

import com.example.demo.models.Task;
import com.example.demo.models.User;

public class AdditionRequest {
    private Long userId;

    private Long taskId;

    public AdditionRequest(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
