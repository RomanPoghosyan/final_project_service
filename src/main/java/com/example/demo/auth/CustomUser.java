package com.example.demo.auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private Long id;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String email) {
        super(username, password, authorities);
        setId(id);
        setEmail(email);
    }
}
