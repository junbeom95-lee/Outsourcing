package com.example.outsourcing.domain.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthPasswordCheckRequest {
    @NotBlank
    private String password;
}
