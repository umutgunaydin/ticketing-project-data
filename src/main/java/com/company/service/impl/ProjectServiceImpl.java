package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.entity.Project;
import com.company.entity.User;
import com.company.enums.Status;
import com.company.mapper.ProjectMapper;
import com.company.repository.ProjectRepository;
import com.company.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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
        projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {
        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}