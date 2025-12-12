package com.example.outsourcing.domain.user.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.PasswordEncoder;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.request.UserDeleteRequest;
import com.example.outsourcing.domain.user.model.request.UserPasswordCheckRequest;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import com.example.outsourcing.domain.user.model.response.*;
import com.example.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ActivityLogSaveUtil activityLogSaveUtil;

    private User user;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "test@test.com", "1234", "jung-hyuk", UserRole.USER);
    }

    @Test
    @DisplayName("회원가입 - 유저 생성이 되는지 확인")
    void signup_saveUser() {

        // given
        UserCreateRequest request = new UserCreateRequest();
        ReflectionTestUtils.setField(request, "username", "testUser");
        ReflectionTestUtils.setField(request, "email", "test@test.com");
        ReflectionTestUtils.setField(request, "password", "1234");
        ReflectionTestUtils.setField(request, "name", "jung-hyuk");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // when

        CommonResponse<UserCreateResponse> response = userService.create(request);

        // then
        assertThat(response).isNotNull();
        assertThat(request.getUsername()).isEqualTo(user.getUsername());
        assertThat(request.getEmail()).isEqualTo(user.getEmail());
        assertThat(request.getPassword()).isEqualTo(user.getPassword());
        assertThat(request.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("유저 단건 조회 - 정보 조회되는 지 확인")
    void findUserId_getUser_information() {

        // given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        CommonResponse<UserGetOneResponse> response = userService.getOne(userId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getData().getId()).isEqualTo(user.getId());
        assertThat(response.getData().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getData().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getData().getName()).isEqualTo(user.getName());
        assertThat(response.getData().getRole()).isEqualTo(user.getRole().name());
    }

    @Test
    @DisplayName("유저 목록 조회 - 목록 조회 되는 지 확인")
    void findByUserList() {

        // given
        when(userRepository.findAll()).thenReturn(List.of(user));

        // when
        CommonResponse<List<UserGetListResponse>> response = userService.getList();

        // then
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData()).hasSize(1);

        UserGetListResponse dto = response.getData().get(0);
        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getUsername()).isEqualTo(user.getUsername());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getName()).isEqualTo(user.getName());
        assertThat(dto.getRole()).isEqualTo(user.getRole().name());
    }

    @Test
    @DisplayName("유저 정보 수정 - 제대로 정보가 수정되는지 확인")
    void updateUser_information() {

        // given
        Long userId = 1L;
        Long id = 1L;

        ReflectionTestUtils.setField(user, "id", 1L);

        UserUpdateRequest request = new UserUpdateRequest();
        ReflectionTestUtils.setField(request, "name", "최정혁");
        ReflectionTestUtils.setField(request, "email", "updateTest@test.com");
        ReflectionTestUtils.setField(request, "password", "1234");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        when(passwordEncoder.matches(request.getPassword(),user.getPassword())).thenReturn(true);

        // when
        CommonResponse<UserUpdateResponse> response = userService.update(userId, id, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getData().getName()).isEqualTo("최정혁");
        assertThat(response.getData().getEmail()).isEqualTo("updateTest@test.com");
        assertThat(response.getData().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getData().getRole()).isEqualTo(user.getRole().name());
    }



    @Test
    @DisplayName("회원 탈퇴 - 정상적으로 softDelete 되는지 확인")
    void deleteUser_success() {

        // given
        Long userId = 1L;
        Long id = 1L;

        ReflectionTestUtils.setField(user, "id", id);

        UserDeleteRequest request = new UserDeleteRequest();
        ReflectionTestUtils.setField(request, "password", "1234");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);

        // when
        CommonResponse<Void> response = userService.delete(userId, id, request);

        // then
        assertThat(response).isNotNull();
    }



    @Test
    @DisplayName("추가 가능한 사용자 조회 - teamId 기준으로 유저 목록 조회 확인")
    void getAddUser_Available_success() {

        // given
        Long teamId = 1L;

        ReflectionTestUtils.setField(user, "id", 1L);

        when(userRepository.findAllUserByUser_idIsNull(teamId)).thenReturn(List.of(user));

        // when
        CommonResponse<List<UserGetAvailableResponse>> response = userService.getAvailable(teamId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getData()).hasSize(1);

        UserGetAvailableResponse dto = response.getData().get(0);

        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getUsername()).isEqualTo(user.getUsername());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getName()).isEqualTo(user.getName());
        assertThat(dto.getRole()).isEqualTo(user.getRole().name());
    }

    @Test
    @DisplayName("비밀번호 확인 - 비밀번호가 일치하면 응답 확인")
    void checkPassword_success() {

        // given
        ReflectionTestUtils.setField(user, "id", 1L);

        UserPasswordCheckRequest request = new UserPasswordCheckRequest();
        ReflectionTestUtils.setField(request, "password", "1234");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.getPassword(),user.getPassword())).thenReturn(true);

        // when
        CommonResponse<UserPasswordCheckResponse> response = userService.checkPassword(user.getId(), request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("비밀번호가 확인되었습니다.");
        assertThat(response.getData().isValid()).isTrue();
    }


}

