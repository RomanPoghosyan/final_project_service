package com.example.demo.dto.responses;

public class TaskAnalitic {
    private String username;

    private Integer didTasks;

    public TaskAnalitic(String username, Integer didTasks) {
        this.username = username;
        this.didTasks = didTasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDidTasks() {
        return didTasks;
    }

    public void setDidTasks(Integer didTasks) {
        this.didTasks = didTasks;
    }
}
