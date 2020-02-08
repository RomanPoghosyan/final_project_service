package com.example.demo.models;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<ProjectUserRoleLink> projectUserRoleLinks;

//    @ManyToMany(mappedBy="roles", targetEntity=User.class)
//    private Collection<User> users = new ArrayList<User>();


//    @JoinTable(name = "USER_PROJECT_ROLE")
//            inverseJoinColumns = @JoinColumn(name = "user_id"),
//            joinColumns = @JoinColumn(name = "project_id"))
//    @MapKeyJoinColumn(name = "user_id")
//    @ElementCollection
//    private Map<Project, User> projectUserMap = new HashMap<>();

//    public Map<Project, User> getProjectUserMap() {
//        return projectUserMap;
//    }
//
//    public void setProjectUserMap(Map<Project, User> projectUserMap) {
//        this.projectUserMap = projectUserMap;
//    }

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
}
