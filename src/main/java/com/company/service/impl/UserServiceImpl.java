package com.company.service.impl;

import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        List<UserDTO> userDTOList = userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        return userDTOList
                ;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {
        User entity = userMapper.convertToEntity(user);
        userRepository.save(entity);
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User entity = userMapper.convertToEntity(userDTO);
        //setId to the updated object and save to db
        entity.setId(userRepository.findByUserName(entity.getUserName()).getId());
        userRepository.save(entity);
        return findByUserName(userDTO.getUserName());
    }
}
