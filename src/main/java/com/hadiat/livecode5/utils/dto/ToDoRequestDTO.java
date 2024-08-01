package com.hadiat.livecode5.utils.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hadiat.livecode5.model.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoRequestDTO {
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private EStatus status;
}
