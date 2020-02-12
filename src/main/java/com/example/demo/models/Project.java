package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectUserRoleLink> projectUserRoleLinks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    @OneToMany
    @JoinColumn(name = "task_status_id")
    private List<TaskStatus> taskStatuses;

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

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @JsonIgnore
    public List<ProjectUserRoleLink> getProjectUserRoleLinks() {
        return projectUserRoleLinks;
    }

    public void setProjectUserRoleLinks(List<ProjectUserRoleLink> projectUserRoleLinks) {
        this.projectUserRoleLinks = projectUserRoleLinks;
    }
}
