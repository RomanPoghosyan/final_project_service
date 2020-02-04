package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save ( User user ) {
        try {
            return userRepository.save(user);
        } catch (Exception exception) {
            return null;
        }
    }

    public Optional<User> findByUsername ( String username ) {
        return userRepository.findByUsername(username);
    }
}
