package com.example.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
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
}
