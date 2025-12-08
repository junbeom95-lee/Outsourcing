package com.example.outsourcing.domain.user.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.user.model.request.UserCreateRequest;
import com.example.outsourcing.domain.user.model.response.UserCreateResponse;
import com.example.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    //TODO 사용자 정보 조회 : getOne()
    //TODO URI : /api/users/{id}, method : GET
    //TODO PathVariable : id (유저 id)
    //TODO data : UserGetOneResponse (id, username, email, name, role, createdAt, updatedAt)


    //TODO 사용자 목록 조회 : getList()
    //TODO URI : /api/users, method : GET
    //TODO data : List<UserGetListResponse> (id, username, email, name, role, createdAt)


    //TODO 사용자 정보 수정 : update()
    //TODO Param : Long id (유저 id), UserUpdateRequest (name, email, password)
    //TODO data : UserUpdateResponse (id, username, email, name, role, createdAt, updatedAt)

    //TODO 회원 탈퇴 : delete()
    //TODO Param : UserDeleteRequest (password)
    //TODO data : null

}
