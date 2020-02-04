package com.example.demo.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_status")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "task_status_id")
    private List<Task> tasks;
}
