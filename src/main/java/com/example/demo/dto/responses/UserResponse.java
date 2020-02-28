package com.example.demo.dto.responses;

import com.example.demo.models.User;

import java.time.LocalDate;

public class UserResponse {

    private Long id;

    private String username;

    private String first_name;

    private String last_name;

    private String email;

    private LocalDate created_at;

    private LocalDate updated_at;

    private String location;

    private String phone_number;

    private String fb_token;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.email = user.getEmail();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
        this.location = user.getLocation();
        this.phone_number = user.getPhoneNumber();
        this.fb_token = user.getFb_token();
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
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

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}



