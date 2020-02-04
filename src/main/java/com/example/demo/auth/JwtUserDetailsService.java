package com.example.demo.auth;

import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.example.demo.models.User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return new CustomUser(user.get().getUsername(),user.get().getPassword(),new ArrayList<>(),
                    user.get().getId(),user.get().getEmail());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
