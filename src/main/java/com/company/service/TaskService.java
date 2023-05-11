package com.company.service;

import com.company.dto.TaskDTO;
import com.company.entity.Task;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);
    TaskDTO findById(Long id);



}
