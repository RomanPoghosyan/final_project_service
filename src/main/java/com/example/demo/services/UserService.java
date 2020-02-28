package com.example.demo.services;

import com.example.demo.dto.requests.ChangeUserRoleRequest;
import com.example.demo.dto.responses.BadResponse;
import com.example.demo.dto.responses.ProjectUserResponse;
import com.example.demo.exceptions.*;
import com.example.demo.models.Project;
import com.example.demo.models.ProjectUserRoleLink;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProjectUserRoleLinkService projectUserRoleLinkService;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, ProjectUserRoleLinkService projectUserRoleLinkService, RoleService roleService) {
        this.userRepository = userRepository;
        this.projectUserRoleLinkService = projectUserRoleLinkService;
        this.roleService = roleService;
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

    public List<ProjectUserResponse> findAllByProjectId (Long id) throws UserNotFound {
        List<ProjectUserRoleLink> projectUserRoleLinks = projectUserRoleLinkService.findAllByProjectId(id);

        return projectUserRoleLinks
                .stream()
                .map(purl -> {
                    User user = purl.getUser();
                    ProjectUserResponse projectUserResponse = new ProjectUserResponse();
                    projectUserResponse.setId(user.getId());
                    projectUserResponse.setUsername(user.getUsername());
                    projectUserResponse.setFirst_name(user.getFirst_name());
                    projectUserResponse.setLast_name(user.getLast_name());
                    projectUserResponse.setEmail(user.getEmail());
                    projectUserResponse.setRoleId(purl.getRole().getId());
                    return projectUserResponse;
                })
                .collect(Collectors.toList());
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

    public List<User> searchByUsername (String username, Long projectId ) throws NoUserSearchResult{
        List<Long> projectUserIds = projectUserRoleLinkService.findAllUsersIdsByProjectId(projectId);

        return userRepository.findByUsernameContaining(username).orElseThrow(NoUserSearchResult::new)
                .stream()
                .filter(u -> !projectUserIds.contains(u.getId()))
                .collect(Collectors.toList());
    }

    public void changeUserRole(ChangeUserRoleRequest changeUserRoleRequest) throws UserNotFound, RoleNotFound {
        Long projectId = changeUserRoleRequest.getProjectId();
        Long userId = changeUserRoleRequest.getUserId();
        Role role = roleService.findById(changeUserRoleRequest.getRoleId());
        ProjectUserRoleLink projectUserRoleLink = projectUserRoleLinkService.findByProjectIdAndUserId(projectId, userId);

        projectUserRoleLink.setRole(role);

        projectUserRoleLinkService.save(projectUserRoleLink);
    }

    public User setFbToken ( String token, Authentication authentication ) {
        User currentUser = userRepository.findByUsername(authentication.getName()).get();
        currentUser.setFb_token(token);
        return currentUser;
    }
}
