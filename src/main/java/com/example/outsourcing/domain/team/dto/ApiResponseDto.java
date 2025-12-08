package com.example.outsourcing.domain.team.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponseDto<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
