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

//    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<ProjectUserRoleLink> projectUserRoleLinks = new ArrayList<>();

//    @ManyToMany(mappedBy="projects", targetEntity=User.class)
//    private Collection<User> users = new ArrayList<User>();


//    @JoinTable(name = "USER_PROJECT_ROLE")
//            inverseJoinColumns = @JoinColumn(name = "user_id"),
//            joinColumns = @JoinColumn(name = "project_id"))
//    @MapKeyJoinColumn(name = "project_id")
//    @ElementCollection
//    private Map<Role, User> roleUserHashMap = new HashMap<>();

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

//    public Map<Role, User> getRoleUserHashMap() {
//        return roleUserHashMap;
//    }
//
//    public void setRoleUserHashMap(Map<Role, User> roleUserHashMap) {
//        this.roleUserHashMap = roleUserHashMap;
//    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
