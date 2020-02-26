package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExists;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return userRepository.findById(id).orElseThrow(UserNotFound::new);
    }

    public User findByUsername ( String username ) throws UserNotFound {
        return userRepository.findByUsername(username).orElseThrow(UserNotFound::new);
    }

    public User findByEmail ( String email ) throws UserNotFound {
        return userRepository.findByEmail(email).orElseThrow(UserNotFound::new);
    }

    public User update (User user, Authentication authentication) throws UserNotFound, UserAlreadyExists {
        User currentUser = findByUsername(authentication.getName());
        if (!(currentUser.getUsername().equals(user.getUsername()))) {
            try {
                findByUsername(user.getUsername());
            } catch (UserNotFound ignored) {
                currentUser.setUsername(user.getUsername());
            }
        }
        if (!(currentUser.getEmail().equals(user.getEmail()))) {
            try {
                findByEmail(user.getEmail());
            } catch (UserNotFound ignored) {
                currentUser.setEmail(user.getEmail());
            }
        }
        currentUser.setFirst_name(user.getFirst_name());
        currentUser.setLast_name(user.getLast_name());
        currentUser.setLocation(user.getLocation());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        return this.save(currentUser);
    }

    public List<User> searchByUsername (String username ) throws UserNotFound {
        return userRepository.findByUsernameContaining(username).orElseThrow(UserNotFound::new);
    }
}
