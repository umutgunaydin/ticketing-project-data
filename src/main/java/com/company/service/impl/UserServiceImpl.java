package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.ProjectService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        List<UserDTO> userDTOList = userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserNameAndIsDeleted(username,false));
    }

    @Override
    public void save(UserDTO user) {
        User entity = userMapper.convertToEntity(user);
        userRepository.save(entity);
    }

//    @Override
//    public void deleteByUserName(String username) {
//        userRepository.deleteByUserName(username);
//    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user1=userRepository.findByUserNameAndIsDeleted(userDTO.getUserName(),false);
        User convertedUser=userMapper.convertToEntity(userDTO);
        convertedUser.setId(user1.getId());
        userRepository.save(convertedUser);

        return findByUserName(userDTO.getUserName());
    }

    @Override
    public void delete(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false);
        if (checkIfUserCanBeDeleted(user)) {
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }
    }
}
