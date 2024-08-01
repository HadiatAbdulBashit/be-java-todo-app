package com.hadiat.livecode5.service.impl;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.model.enums.Role;
import com.hadiat.livecode5.repository.ToDoRepository;
import com.hadiat.livecode5.repository.UserRepository;
import com.hadiat.livecode5.service.AuthenticationService;
import com.hadiat.livecode5.service.AdminService;
import com.hadiat.livecode5.utils.dto.RegisterRequestDTO;
import com.hadiat.livecode5.utils.dto.UserResponseDTO;
import com.hadiat.livecode5.utils.dto.UserRoleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository UserRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final ToDoRepository toDoRepository;

    @Override
    public UserResponseDTO createSuperAdmin(RegisterRequestDTO newSuperAdmin) {
        User user = User.builder()
                .username(newSuperAdmin.getUsername())
                .email(newSuperAdmin.getEmail())
                .password(passwordEncoder.encode(newSuperAdmin.getPassword()))
                .role(Role.SUPER_ADMIN)
                .build();
        var savedAdmin = UserRepository.save(user);
        return UserResponseDTO.builder()
                .id(savedAdmin.getId())
                .username(savedAdmin.getUsername())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getRole())
                .createdAt(savedAdmin.getCreatedAt())
                .build();
    }

    @Override
    public UserResponseDTO createAdmin(RegisterRequestDTO newAdmin) {
        User user = User.builder()
                .username(newAdmin.getUsername())
                .email(newAdmin.getEmail())
                .password(passwordEncoder.encode(newAdmin.getPassword()))
                .role(Role.ADMIN)
                .build();
        var savedAdmin = UserRepository.save(user);
        return UserResponseDTO.builder()
                .id(savedAdmin.getId())
                .username(savedAdmin.getUsername())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getRole())
                .createdAt(savedAdmin.getCreatedAt())
                .build();
    }

    @Override
    public Page<User> findAllUser(Pageable pageable) {
        return UserRepository.findAll(pageable);
    }

    @Override
    public User findUserById(Integer id) {
        return UserRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public User updateRoleById(Integer id, UserRoleRequestDTO userRoleRequestDTO) {
        User selectedUser = findUserById(id);
        selectedUser.setRole(userRoleRequestDTO.getRole());
        return UserRepository.save(selectedUser);
    }

    @Override
    public void deleteById(Integer id) {
        UserRepository.deleteById(id);
    }

    @Override
    public Page<ToDo> findAllToDo(Pageable pageable) {
        return toDoRepository.findAll(pageable);
    }

    @Override
    public ToDo findToDoById(Integer id) {
        return toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("To Do Not Found"));
    }
}
