package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.dto.UserDTO;
import com.company.entity.Project;
import com.company.entity.User;
import com.company.enums.Status;
import com.company.mapper.ProjectMapper;
import com.company.mapper.UserMapper;
import com.company.repository.ProjectRepository;
import com.company.service.ProjectService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projects=projectRepository.findAll(Sort.by("projectCode"));
        return projects.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        projectRepository.save(projectMapper.convertToEntity(dto));
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project=projectRepository.findByProjectCode(dto.getProjectCode());
        Project entity=projectMapper.convertToEntity(dto);
        entity.setId(project.getId());
        entity.setProjectStatus(project.getProjectStatus());
        projectRepository.save(entity);
    }

    @Override
    public void delete(String code) {
        Project project=projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode()+"-"+project.getId());//to use code later

        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDto(project)); //to delete related tasks
    }

    @Override
    public void complete(String projectCode) {
        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDto(project)); //to complete related tasks

    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {


        UserDTO currentUserDto=userService.findByUserName("harold@manager.com");
        User user=userMapper.convertToEntity(currentUserDto);

        List<Project> projectList=projectRepository.findAllByAssignedManager(user);

        return projectList.stream().map(project -> {
            ProjectDTO dto=projectMapper.convertToDto(project);
            dto.setCompleteTaskCounts(taskService.totalCompletedTasks(project.getProjectCode()));
            dto.setUnfinishedTaskCounts(taskService.totalNonCompletedTasks(project.getProjectCode()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO userDTO) {
        List<Project> projects=projectRepository
                .findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE,userMapper.convertToEntity(userDTO));

        return projects.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }
}
