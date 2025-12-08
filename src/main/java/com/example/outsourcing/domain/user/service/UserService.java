package com.example.outsourcing.domain.user.service;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.common.util.PasswordEncoder;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.request.UserDeleteRequest;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import com.example.outsourcing.domain.user.model.response.UserCreateResponse;
import com.example.outsourcing.domain.user.model.response.UserGetListResponse;
import com.example.outsourcing.domain.user.model.response.UserGetOneResponse;
import com.example.outsourcing.domain.user.model.response.UserUpdateResponse;
import com.example.outsourcing.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입
    @Transactional
    public CommonResponse<UserCreateResponse> create(UserCreateRequest request) {

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) throw new CustomException(ExceptionCode.EXISTS_EMAIL);

        boolean exitsUsername = userRepository.existsByUsername(request.getUsername());

        if (exitsUsername) throw new CustomException(ExceptionCode.EXISTS_USERNAME);

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName());

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
    public CommonResponse<UserUpdateResponse> update(Long id, @Valid UserUpdateRequest request) {

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) throw new CustomException(ExceptionCode.EXISTS_EMAIL);

        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (matches) {

            user.update(request);

            UserUpdateResponse response = UserUpdateResponse.from(user);

            return new CommonResponse<>(true, "사용자 정보가 수정되었습니다.", response);
        }

        throw new CustomException(ExceptionCode.NOT_MATCHES_PASSWORD);
    }

    //회원 탈퇴
    @Transactional
    public CommonResponse<Void> delete(Long id, UserDeleteRequest request) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (matches) {

            user.softDelete();

            return new CommonResponse<>(true, "회원 탈퇴가 완료되었습니다.", null);
        }

        throw new CustomException(ExceptionCode.NOT_MATCHES_PASSWORD);
    }

}
