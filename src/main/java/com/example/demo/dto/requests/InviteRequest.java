package com.example.demo.dto.requests;

public class InviteRequest {

    private Long invitedId;

    private Long projectId;

    public InviteRequest(Long invitedId, Long projectId) {
        this.invitedId = invitedId;
        this.projectId = projectId;
    }

    public Long getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(Long invitedId) {
        this.invitedId = invitedId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
