package com.example.demo.dto.requests;

public class AddProjectRequest {
    private Long userId;
    private String projectName;

    public AddProjectRequest(Long userId, String projectName) {
        this.userId = userId;
        this.projectName = projectName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
