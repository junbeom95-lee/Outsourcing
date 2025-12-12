package com.example.outsourcing.domain.auth.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.JwtUtil;
import com.example.outsourcing.common.util.PasswordEncoder;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.auth.model.request.AuthLoginRequest;
import com.example.outsourcing.domain.auth.model.response.AuthTokenResponse;
import com.example.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private  ActivityLogSaveUtil activityLogSaveUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 성공 - 유효한 사용자의 유저명과 비밀번호가 주어졌을 때")
    void login_success() {

        // given
        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", "testUser");
        ReflectionTestUtils.setField(request, "password", "1234");

        User user = new User("testUser", "test@test.com", "1234", "jung-hyuk", UserRole.USER);
        ReflectionTestUtils.setField(user, "id", 1L);

        // when
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "1234")).thenReturn(true);
        when(jwtUtil.generateToken(1L, "testUser", UserRole.USER)).thenReturn("token");

        CommonResponse<AuthTokenResponse> response = authService.login(request);

        // then

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getToken()).isEqualTo("token");
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자명일 경우 예외 발생")
    void login_fail_user_not_found() {
        // given
        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", "notUser");
        ReflectionTestUtils.setField(request, "password", "1234");

        // when
        when(userRepository.findByUsername("notUser")).thenReturn(Optional.empty());

        // then
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> {authService.login(request);});

        assertThat(exception.getExceptionCode()).isEqualTo(ExceptionCode.NOT_FOUND_USER);
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치로 예외 발생")
    void login_fail_password_not_match() {
        // given
        AuthLoginRequest request = new AuthLoginRequest();
        ReflectionTestUtils.setField(request, "username", "testUser");
        ReflectionTestUtils.setField(request, "password", "wrong");

        User user = new User("testUser", "test@test.com", "1234", "jung-hyuk", UserRole.USER);

        // when
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "1234")).thenReturn(false);

        // then
        CustomException exception = Assertions.assertThrows(CustomException.class, () -> {authService.login(request);});

        assertThat(exception.getExceptionCode()).isEqualTo(ExceptionCode.NOT_MATCHES_PASSWORD);
    }


}