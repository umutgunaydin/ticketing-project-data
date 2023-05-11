package com.company.service.impl;

import com.company.dto.TaskDTO;
import com.company.entity.Task;
import com.company.enums.Status;
import com.company.mapper.TaskMapper;
import com.company.repository.TaskRepository;
import com.company.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        taskRepository.save(taskMapper.convertToEntity(dto));
    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task=taskRepository.findById(dto.getId());
        Task converted=taskMapper.convertToEntity(dto);

        if (task.isPresent()) {
            converted.setTaskStatus(task.get().getTaskStatus());
            converted.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(converted);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task=taskRepository.findById(id);
        if (task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task=taskRepository.findById(id);
        if (task.isPresent()){
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }
}
