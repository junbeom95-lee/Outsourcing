package com.example.outsourcing.domain.user.service;

import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //TODO 회원가입 : create()
    //TODO Param : UserCreateRequest (username, email, password, name)
    //TODO Return data : UserCreateResponse (id, username, email, name, role, createdAt)


    //TODO 사용자 정보 조회 : getOne()
    //TODO Param : Long id (유저 id)
    //TODO Return data : UserGetOneResponse (id, username, email, name, role, createdAt, updatedAt)


    //TODO 사용자 목록 조회 : getList()
    //TODO Return data : List<UserGetListResponse> (id, username, email, name, role, createdAt)


    //TODO 사용자 정보 수정 : update()
    //TODO Param : Long id (유저 id), UserUpdateRequest (name, email, password)
    //TODO Return data : UserUpdateResponse (id, username, email, name, role, createdAt, updatedAt)


    //TODO 회원 탈퇴 delete()
    //TODO URI : /api/users/{id}, method: DELETE
    //TODO Param : Long id (유저 id), UserDeleteRequest (password)
    //TODO Return data : null
}
