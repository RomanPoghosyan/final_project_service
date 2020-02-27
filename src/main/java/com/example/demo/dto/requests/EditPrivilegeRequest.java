package com.example.demo.dto.requests;

public class EditPrivilegeRequest {

    private Long roleId;

    private Long privilegeId;

    private Boolean isAddition;

    public EditPrivilegeRequest(Long roleId, Long privilegeId, Boolean isAddition) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
        this.isAddition = isAddition;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Boolean getAddition() {
        return isAddition;
    }

    public void setAddition(Boolean addition) {
        isAddition = addition;
    }
}
