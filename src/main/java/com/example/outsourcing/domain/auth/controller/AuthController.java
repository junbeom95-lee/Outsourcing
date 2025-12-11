package com.example.outsourcing.domain.auth.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.auth.model.request.AuthLoginRequest;
import com.example.outsourcing.domain.auth.model.response.AuthTokenResponse;
import com.example.outsourcing.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthTokenResponse>> loginUser (@Valid @RequestBody AuthLoginRequest request){

        CommonResponse<AuthTokenResponse> response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
