package com.hadiat.livecode5.utils.dto;

import com.hadiat.livecode5.model.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoStatusRequestDTO {
    private EStatus status;
}
