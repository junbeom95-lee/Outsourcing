package com.example.outsourcing.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPasswordCheckResponse {

    private final boolean valid;

    public static UserPasswordCheckResponse from(boolean valid){
        return new UserPasswordCheckResponse(valid);
    }
}
