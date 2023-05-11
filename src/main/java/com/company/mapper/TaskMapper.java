package com.company.mapper;

import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.Task;
import com.company.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Task convertToEntity(TaskDTO dto){
        return modelMapper.map(dto,Task.class);
    }

    public TaskDTO convertToDto(Task entity){
        return modelMapper.map(entity,TaskDTO.class);
    }
}
