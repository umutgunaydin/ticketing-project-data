package com.company.service;

import com.company.dto.RoleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id);

}
