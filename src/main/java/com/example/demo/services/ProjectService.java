package com.example.demo.services;

import com.example.demo.dto.requests.ColumnReorderRequest;
import com.example.demo.dto.responses.ProjectResponse;
import com.example.demo.dto.responses.TaskAnalitic;
import com.example.demo.dto.responses.TaskMiniInfoResponse;
import com.example.demo.exceptions.ProjectNotFound;
import com.example.demo.exceptions.ProjectsByUserIdNotFound;
import com.example.demo.exceptions.RoleNotFound;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.models.*;
import com.example.demo.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRoleLinkService projectUserRoleLinkService;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectUserRoleLinkService projectUserRoleLinkService, RoleService roleService, UserService userService) {

        this.projectRepository = projectRepository;
        this.projectUserRoleLinkService = projectUserRoleLinkService;
        this.roleService = roleService;
        this.userService = userService;
    }


    public Project add(Project project, Authentication authentication) throws UserNotFound, RoleNotFound {
        Role leadRole = roleService.findById(1L);
        Role memberRole = roleService.findById(2L);
        project.getRoles().addAll(Arrays.asList(leadRole, memberRole));
        Project savedProject = projectRepository.save(project);
        User user = userService.findByUsername(authentication.getName());
        projectUserRoleLinkService.add(savedProject, user, leadRole);
        return savedProject;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findById ( Long id ) throws ProjectNotFound {
        if(projectRepository.findById(id).isPresent()){
            return projectRepository.findById(id).get();
        }else{
            throw new ProjectNotFound();
        }
    }

    public ProjectResponse getFullProjectInfo(Long id, Authentication authentication ) throws ProjectNotFound {
        if(projectRepository.findById(id).isPresent()){
            Project project = projectRepository.findById(id).get();
            AtomicBoolean allow = new AtomicBoolean(false);
            project.getProjectUserRoleLinks().forEach(purl -> {
                if (authentication.getName().equals(purl.getUser().getUsername())) allow.set(true);
            });
            if(allow.get()) {
                ProjectResponse projectResponse = new ProjectResponse();
                projectResponse.setId(project.getId());
                projectResponse.setName(project.getName());
                project.getTasks()
                        .forEach(t -> {
                            Long assignee_id = t.getAssignee() != null ? t.getAssignee().getId() : null;
//                            TaskMiniInfoResponse tmir = new TaskMiniInfoResponse(t.getId(), t.getTitle(), assignee_id, t.getMicro_tasks());
                            TaskMiniInfoResponse tmir = new TaskMiniInfoResponse(t.getId(), t.getTitle(), assignee_id, t.getMicro_tasks());
                            projectResponse.getTasks().put(tmir.getId(), tmir);
                        });

                Map<Long, TaskStatus> taskStatusMap = project.getTaskStatuses()
                        .stream().collect(Collectors.toMap(TaskStatus::getId, Function.identity()));

                projectResponse.setColumns(taskStatusMap);
                projectResponse.setColumnOrder(project.getTaskStatusesOrder());
                return projectResponse;


//                Map<Long, Project> projectMap = projectRepository.findById(1L)
//                        .stream().collect(Collectors.toMap(Project::getId, Function.identity()));
            }else {
                throw new ProjectNotFound();
            }
        }else{
            throw new ProjectNotFound();
        }
//            return projectRepository.findById(id).orElseThrow(ProjectNotFound::new);
    }

    public List<Project> findAllByUserId (Long id) throws ProjectsByUserIdNotFound {
        List<ProjectUserRoleLink> projectUserRoleLinks = projectUserRoleLinkService.findAllByUserId(id);

        return projectUserRoleLinks
                .stream()
                .map(ProjectUserRoleLink::getProject)
                .collect(Collectors.toList());
    }

    public List<Long> updateColumnOrder(ColumnReorderRequest columnReorderRequest, Authentication authentication) throws ProjectNotFound {
        Project project = projectRepository.findById(columnReorderRequest.getProjectId()).orElseThrow(ProjectNotFound::new);
        project.setTaskStatusesOrder(columnReorderRequest.getColumnOrder());
        projectRepository.save(project);
        return columnReorderRequest.getColumnOrder();
    }

    public List<TaskAnalitic> getProjectData (Long projectId ) throws ProjectNotFound {
        Project project = findById(projectId);
        List<ProjectUserRoleLink> projectUserRoleLinks = project.getProjectUserRoleLinks();
        List<TaskAnalitic> taskAnalitics = new ArrayList<>();
        for(ProjectUserRoleLink projectUserRoleLink: projectUserRoleLinks) {
            TaskAnalitic taskAnalitic = new TaskAnalitic(
                    projectUserRoleLink.getUser().getUsername(),
                    projectUserRoleLink.getUser().getAssigned_tasks().size()
            );
            taskAnalitics.add(taskAnalitic);
        }
        return taskAnalitics;
    }
}

