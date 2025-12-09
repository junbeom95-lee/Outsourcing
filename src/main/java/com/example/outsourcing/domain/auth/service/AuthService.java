package com.example.outsourcing.domain.auth.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.JwtUtil;
import com.example.outsourcing.common.util.PasswordEncoder;
import com.example.outsourcing.domain.auth.model.request.AuthLoginRequest;
import com.example.outsourcing.domain.auth.model.response.AuthTokenResponse;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public CommonResponse<AuthTokenResponse> login(AuthLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){throw new CustomException(ExceptionCode.NOT_MATCHES_PASSWORD);}

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        AuthTokenResponse response = AuthTokenResponse.from(token);

        return new CommonResponse<>(true, "로그인 성공",response);

    }
}
