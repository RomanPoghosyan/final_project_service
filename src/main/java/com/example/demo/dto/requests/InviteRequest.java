package com.example.demo.dto.requests;

public class InviteRequest {

    private String username;

    private Long projectId;

    private Long roleId;

    public InviteRequest(String username, Long projectId, Long roleId) {
        this.username = username;
        this.projectId = projectId;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
