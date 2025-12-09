package com.example.outsourcing.domain.auth.model.response;

import com.example.outsourcing.common.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTokenResponse {
    private String token;

    public static  AuthTokenResponse from(String token){
        return new AuthTokenResponse(token);
    }

}
