package com.example.demo.dto.requests;

import java.util.List;

public class AddRoleRequest {

    private String name;

    private Long projectId;

    private List<Long> privilegesIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<Long> getPrivilegesIds() {
        return privilegesIds;
    }

    public void setPrivilegesIds(List<Long> privilegesIds) {
        this.privilegesIds = privilegesIds;
    }
}
