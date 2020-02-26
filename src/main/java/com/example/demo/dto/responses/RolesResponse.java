package com.example.demo.dto.responses;

import com.example.demo.models.RoleType;

import java.util.List;

public class RolesResponse {
    private Long id;
    private String name;
    private RoleType type;
    private List<Long> privilegesIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public List<Long> getPrivilegesIds() {
        return privilegesIds;
    }

    public void setPrivilegesIds(List<Long> privilegesIds) {
        this.privilegesIds = privilegesIds;
    }
}
