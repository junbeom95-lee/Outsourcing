package com.example.outsourcing.domain.user.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.user.model.request.UserPasswordCheckRequest;
import com.example.outsourcing.domain.user.model.response.UserPasswordCheckResponse;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.request.UserDeleteRequest;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import com.example.outsourcing.domain.user.model.response.*;
import com.example.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;


    //회원가입
    @PostMapping()
    public ResponseEntity<CommonResponse<UserCreateResponse>> create(@RequestBody @Valid UserCreateRequest request) {

        log.info("회원가입 요청 들어옴");
        CommonResponse<UserCreateResponse> response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //사용자 정보 조회 (단건)
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserGetOneResponse>> getOne( @PathVariable Long id) {

        CommonResponse<UserGetOneResponse> response = userService.getOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //사용자 목록 조회
    @GetMapping()
    public ResponseEntity<CommonResponse<List<UserGetListResponse>>> getList() {

        CommonResponse<List<UserGetListResponse>> response = userService.getList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //사용자 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserUpdateResponse>> update(@AuthenticationPrincipal Long userId,
                                                                     @PathVariable Long id,
                                                                     @RequestBody @Valid UserUpdateRequest request) {

        CommonResponse<UserUpdateResponse> response = userService.update(userId, id, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@AuthenticationPrincipal Long userId,
                                                       @PathVariable Long id,
                                                       @RequestBody @Valid UserDeleteRequest request) {

        CommonResponse<Void> response = userService.delete(userId, id, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //추가 가능한 사용자 조회
    @GetMapping("/available")
    public ResponseEntity<CommonResponse<List<UserGetAvailableResponse>>> getAvailable(@RequestParam(required = false) Long teamId) {

        CommonResponse<List<UserGetAvailableResponse>> response = userService.getAvailable(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //비밀번호 확인
    @PostMapping("/verify-password")
    public ResponseEntity<CommonResponse<UserPasswordCheckResponse>> checkingPassword(@AuthenticationPrincipal Long userId,
                                                                                      @RequestBody @Valid UserPasswordCheckRequest request) {

        CommonResponse<UserPasswordCheckResponse> response = userService.checkPassword(userId, request);

        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }

}
