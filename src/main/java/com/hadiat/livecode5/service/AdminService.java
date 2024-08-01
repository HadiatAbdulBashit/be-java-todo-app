package com.hadiat.livecode5.service;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.utils.dto.RegisterRequestDTO;
import com.hadiat.livecode5.utils.dto.UserResponseDTO;
import com.hadiat.livecode5.utils.dto.UserRoleRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    UserResponseDTO createSuperAdmin(RegisterRequestDTO newSuperAdmin);
    UserResponseDTO createAdmin(RegisterRequestDTO newAdmin);
    Page<User> findAllUser(Pageable pageable);
    User findUserById(Integer id);
    User updateRoleById(Integer id, UserRoleRequestDTO userRoleRequestDTO);
    void deleteById(Integer id);

    Page<ToDo> findAllToDo(Pageable pageable);
    ToDo findToDoById(Integer id);
}
