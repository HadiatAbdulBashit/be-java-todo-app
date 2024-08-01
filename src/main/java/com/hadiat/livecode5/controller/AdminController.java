package com.hadiat.livecode5.controller;

import com.hadiat.livecode5.model.ToDo;
import com.hadiat.livecode5.model.User;
import com.hadiat.livecode5.service.AdminService;
import com.hadiat.livecode5.utils.dto.RegisterRequestDTO;
import com.hadiat.livecode5.utils.dto.UserResponseDTO;
import com.hadiat.livecode5.utils.dto.UserRoleRequestDTO;
import com.hadiat.livecode5.utils.responseWrapper.PaginationResponse;
import com.hadiat.livecode5.utils.responseWrapper.PaginationUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService AdminService;

    @PostMapping("/super-admin")
    public ResponseEntity<UserResponseDTO> createSuperAdmin(@RequestBody RegisterRequestDTO request) {
        return new ResponseEntity<>(AdminService.createSuperAdmin(request), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createAdmin(@RequestBody RegisterRequestDTO request) {
        return new ResponseEntity<>(AdminService.createAdmin(request), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<PaginationUserResponse<UserResponseDTO>> findAllUsers(@PageableDefault Pageable pageable) {
        Page<User> userPage = AdminService.findAllUser(pageable);
        Page<UserResponseDTO> userResponseDTOPage = userPage.map(user -> UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build());
        return new ResponseEntity<>(new PaginationUserResponse<>(userResponseDTOPage), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Integer id) {
        User user = AdminService.findUserById(id);
        return new ResponseEntity<>(UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build(), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserResponseDTO> updateRoleUserById(@PathVariable Integer id, @RequestBody UserRoleRequestDTO userRoleRequestDTO) {
        User user = AdminService.updateRoleById(id, userRoleRequestDTO);
        return new ResponseEntity<>(UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<?> findAllToDo(@PageableDefault Pageable pageable) {
        Page<ToDo> toDoPage = AdminService.findAllToDo(pageable);
        return new ResponseEntity<>(new PaginationResponse<>(toDoPage), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDo> findToDoById(@PathVariable Integer id) {
        return new ResponseEntity<>(AdminService.findToDoById(id), HttpStatus.OK);
    }
}
