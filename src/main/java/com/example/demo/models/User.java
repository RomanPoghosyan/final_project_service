package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "created_tasks"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ProjectUserRoleLink> projectUserRoleLinks;

    @JsonIgnore
    public List<ProjectUserRoleLink> getProjectUserRoleLinks() {
        return projectUserRoleLinks;
    }

    public void setProjectUserRoleLinks(List<ProjectUserRoleLink> projectUserRoleLinks) {
        this.projectUserRoleLinks = projectUserRoleLinks;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Comment> comments;

//    TODO is everything ok?
    @OneToMany
    @JoinColumn(name = "assignor_id")
    private List<Task> created_tasks;

    @OneToMany
    @JoinColumn(name = "assignee_id")
    private List<Task> assigned_tasks;

    @OneToMany
    @JoinColumn(name = "notified_by_id")
    private List<Notification> notifications_by;

    @OneToMany
    @JoinColumn(name = "notified_to_id")
    private List<Notification> notifications_to;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    private Date updated_at;

    private String location;

    private String phoneNumber;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getChanged_at() {
        return updated_at;
    }

    public void setChanged_at(Date changed_at) {
        this.updated_at = changed_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Task> getCreated_tasks() {
        return created_tasks;
    }

    public void setCreated_tasks(List<Task> created_tasks) {
        this.created_tasks = created_tasks;
    }

    public List<Task> getAssigned_tasks() {
        return assigned_tasks;
    }

    public void setAssigned_tasks(List<Task> assigned_tasks) {
        this.assigned_tasks = assigned_tasks;
    }

    public List<Notification> getNotifications_by() {
        return notifications_by;
    }

    public void setNotifications_by(List<Notification> notifications_by) {
        this.notifications_by = notifications_by;
    }

    public List<Notification> getNotifications_to() {
        return notifications_to;
    }

    public void setNotifications_to(List<Notification> notifications_to) {
        this.notifications_to = notifications_to;
    }
}
