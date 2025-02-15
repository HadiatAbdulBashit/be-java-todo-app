package com.hadiat.livecode5.utils.responseWrapper;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponseError<T> {
    private String message;
    private List<T> errors;
}