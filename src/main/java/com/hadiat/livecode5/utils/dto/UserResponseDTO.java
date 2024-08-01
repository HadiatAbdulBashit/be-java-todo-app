package com.hadiat.livecode5.utils.dto;

import com.hadiat.livecode5.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
