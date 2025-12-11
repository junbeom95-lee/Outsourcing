package com.example.outsourcing.domain.user.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPasswordCheckResponse {
    private final boolean valid;

    public static UserPasswordCheckResponse from(boolean valid){
        return new UserPasswordCheckResponse(valid);
    }
}
