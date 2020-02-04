package com.example.demo.models;


import javax.persistence.*;

@Entity
@Table(name = "micro_tasks")
public class MicroTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MicroTaskStatus status;

    @ManyToOne
    private Task task;
}
