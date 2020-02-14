package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
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

    public User save ( User user ) throws UserAlreadyExists {
        try {
            return userRepository.save(user);
        } catch (Exception exception) {
            throw new UserAlreadyExists();
        }
    }

    public User findById (Long id) throws UserNotFound {
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }
        throw new UserNotFound();
    }

    public User findByUsername ( String username ) throws UserNotFound {
        if(userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).get();
        }
        throw new UserNotFound();
    }

    public User findByEmail ( String email ) throws UserNotFound {
        if ( userRepository.findByEmail(email).isPresent() ) {
            return userRepository.findByEmail(email).get();
        }
        throw new UserNotFound();
    }
}
