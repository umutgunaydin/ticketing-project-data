package com.company.service;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.Task;
import com.company.entity.User;
import com.company.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);
    TaskDTO findById(Long id);
    int totalCompletedTasks(String projectCode);
    int totalNonCompletedTasks(String projectCode);
    void deleteByProject(ProjectDTO projectDTO);
    void completeByProject(ProjectDTO projectDTO);
    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByStatus(Status status);
    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee);
}
