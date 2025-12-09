package com.example.outsourcing.domain.auth.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthPasswordCheckResponse {
    private final boolean valid;

    public static AuthPasswordCheckResponse from(boolean valid){
        return new AuthPasswordCheckResponse(valid);
    }
}
