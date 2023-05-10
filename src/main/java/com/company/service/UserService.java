package com.company.service;

import com.company.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO user);

    void deleteByUserName(String username);

    UserDTO update(UserDTO userDTO);
    void delete(String username);


}
