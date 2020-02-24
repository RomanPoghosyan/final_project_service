package com.example.demo.dto.responses;

import com.example.demo.models.MicroTask;

import java.util.List;

public class RolesResponse {
    private Long id;
    private String name;
    private  List<Long> privileges;

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

    public List<Long> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Long> privileges) {
        this.privileges = privileges;
    }
}
