package com.example.demo.dto.requests;

public class ChangeUserRoleRequest {

    private Long roleId;

    private Long projectId;

    private Long userId;

    public ChangeUserRoleRequest(Long roleId, Long projectId, Long userId) {
        this.roleId = roleId;
        this.projectId = projectId;
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
