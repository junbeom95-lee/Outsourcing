package com.example.outsourcing.domain.user.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import com.example.outsourcing.domain.user.model.response.UserCreateResponse;
import com.example.outsourcing.domain.user.model.response.UserGetListResponse;
import com.example.outsourcing.domain.user.model.response.UserGetOneResponse;
import com.example.outsourcing.domain.user.model.response.UserUpdateResponse;
import com.example.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    //회원가입
    @PostMapping()
    public ResponseEntity<CommonResponse<UserCreateResponse>> create(@RequestBody @Valid UserCreateRequest request) {

        CommonResponse<UserCreateResponse> response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //사용자 정보 조회 (단건)
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserGetOneResponse>> getOne(@PathVariable Long id) {

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
    public ResponseEntity<CommonResponse<UserUpdateResponse>> update(@PathVariable Long id,
                                                                     @RequestBody @Valid UserUpdateRequest request) {

        CommonResponse<UserUpdateResponse> response = userService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //TODO 회원 탈퇴 : delete()
    //TODO Param : UserDeleteRequest (password)
    //TODO data : null

}
