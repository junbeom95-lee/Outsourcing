package com.example.outsourcing.domain.user.controller;

import com.example.outsourcing.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    //TODO 회원가입 : create()
    //TODO URI : /api/users, method : POST
    //TODO RequestBody : UserCreateRequest (username, email, password, name)
    //TODO data : UserCreateResponse (id, username, email, name, role, createdAt)


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
