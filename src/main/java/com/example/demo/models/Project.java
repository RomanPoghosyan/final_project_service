package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "projects")
@Data
@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "projectUserRoleLinks", "tasks", "taskStatuses" }, allowSetters= true)
//@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ProjectUserRoleLink> projectUserRoleLinks = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<TaskStatus> taskStatuses;

    private Long[] taskStatusesOrder;

    public Long[] getTaskStatusesOrder() {
        return taskStatusesOrder;
    }

    public void setTaskStatusesOrder(Long[] taskStatusesOrder) {
        this.taskStatusesOrder = taskStatusesOrder;
    }

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

//    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<TaskStatus> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(List<TaskStatus> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    //    @JsonIgnore
    public List<ProjectUserRoleLink> getProjectUserRoleLinks() {
        return projectUserRoleLinks;
    }

    public void setProjectUserRoleLinks(List<ProjectUserRoleLink> projectUserRoleLinks) {
        this.projectUserRoleLinks = projectUserRoleLinks;
    }
}
