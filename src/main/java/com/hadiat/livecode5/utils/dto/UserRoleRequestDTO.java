package com.hadiat.livecode5.utils.dto;

import com.hadiat.livecode5.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestDTO {
    private Role role;
}
