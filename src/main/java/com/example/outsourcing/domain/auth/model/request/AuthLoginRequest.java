package com.example.outsourcing.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
