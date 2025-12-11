package com.example.outsourcing.domain.user.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.PasswordEncoder;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.request.UserDeleteRequest;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import com.example.outsourcing.domain.user.model.response.*;
import com.example.outsourcing.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivityLogSaveUtil activityLogSaveUtil;

    //회원가입
    @Transactional
    public CommonResponse<UserCreateResponse> create(UserCreateRequest request) {

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) throw new CustomException(ExceptionCode.EXISTS_EMAIL);

        boolean exitsUsername = userRepository.existsByUsername(request.getUsername());

        if (exitsUsername) throw new CustomException(ExceptionCode.EXISTS_USERNAME);

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName(), UserRole.USER);

        User savedUser = userRepository.save(user);

        UserCreateResponse response = UserCreateResponse.from(savedUser);

        return new CommonResponse<>(true, "회원가입이 완료되었습니다.", response);
    }

    //사용자 정보 조회 (단건)
    @Transactional(readOnly = true)
    public CommonResponse<UserGetOneResponse> getOne(Long id) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        UserGetOneResponse response = UserGetOneResponse.from(user);

        return new CommonResponse<>(true, "사용자 정보 조회 성공", response);
    }

    //사용자 목록 조회
    @Transactional(readOnly = true)
    public CommonResponse<List<UserGetListResponse>> getList() {

        List<User> userList = userRepository.findAll();

        List<UserGetListResponse> userGetListResponseList = userList.stream()
                .map(UserGetListResponse::from)
                .toList();

        return new CommonResponse<>(true, "사용자 목록 조회 성공", userGetListResponseList);
    }

    //사용자 정보 수정
    @Transactional
    public CommonResponse<UserUpdateResponse> update(Long userId, Long id, @Valid UserUpdateRequest request) {

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) throw new CustomException(ExceptionCode.EXISTS_EMAIL);

        User user = getUser(userId, id, request.getPassword());

        user.update(request);

        UserUpdateResponse response = UserUpdateResponse.from(user);

        return new CommonResponse<>(true, "사용자 정보가 수정되었습니다.", response);

    }

    //회원 탈퇴
    @Transactional
    public CommonResponse<Void> delete(Long userId, Long id, UserDeleteRequest request) {

        User user = getUser(userId, id, request.getPassword());

        user.softDelete();

        activityLogSaveUtil.saveActivityUserLogout(user);

        return new CommonResponse<>(true, "회원 탈퇴가 완료되었습니다.", null);

    }

    //회원 검증 (id, password)
    private User getUser(Long userId, Long id, String password) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        if (!Objects.equals(user.getId(), userId)) throw new CustomException(ExceptionCode.FORBIDDEN);

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if (!matches) throw new CustomException(ExceptionCode.NOT_MATCHES_PASSWORD);

        return user;
    }

    //추가 가능한 사용자 조회
    public CommonResponse<List<UserGetAvailableResponse>> getAvailable(Long teamId) {

        List<User> userList = userRepository.findAllUserByUser_idIsNull(teamId);

        List<UserGetAvailableResponse> response = userList.stream().map(UserGetAvailableResponse::from).toList();

        return new CommonResponse<>(true, "추가 가능한 사용자 목록 조회 성공", response);
    }
}
