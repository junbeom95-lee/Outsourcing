package com.example.outsourcing.domain.search.dto;

import com.example.outsourcing.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSearchResponse {

    private final Long id;
    private final String name;
    private final String username;

    public static UserSearchResponse from(User user){
        return new UserSearchResponse(user.getId(), user.getName(), user.getUsername());}
}
