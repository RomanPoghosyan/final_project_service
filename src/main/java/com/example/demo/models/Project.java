package com.example.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectUserRoleLink> projectUserRoleLinks;

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

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

    public List<ProjectUserRoleLink> getProjectUserRoleLinks() {
        return projectUserRoleLinks;
    }

    public void setProjectUserRoleLinks(List<ProjectUserRoleLink> projectUserRoleLinks) {
        this.projectUserRoleLinks = projectUserRoleLinks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
