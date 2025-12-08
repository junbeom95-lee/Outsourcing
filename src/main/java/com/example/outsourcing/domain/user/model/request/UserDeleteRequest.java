package com.example.outsourcing.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequest {
    @NotBlank
    private String password;
}
