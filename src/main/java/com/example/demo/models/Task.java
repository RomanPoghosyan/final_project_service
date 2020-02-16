package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "project", "task_status" }, allowSetters= true)
//@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToMany
    @JoinColumn(name = "task_id")
    private List<MicroTask> micro_tasks;

    @ManyToOne
    @JoinColumn(name = "task_status_id", nullable = false)
    private TaskStatus task_status;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignor;

    @ManyToOne
    private User assignee;

    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MicroTask> getMicro_tasks() {
        return micro_tasks;
    }

    public void setMicro_tasks(List<MicroTask> micro_tasks) {
        this.micro_tasks = micro_tasks;
    }

    public TaskStatus getTask_status() {
        return task_status;
    }

    public void setTask_status(TaskStatus task_status) {
        this.task_status = task_status;
    }

    @JsonIgnore
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignor() {
        return assignor;
    }

    public void setAssignor(User assignor) {
        this.assignor = assignor;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
