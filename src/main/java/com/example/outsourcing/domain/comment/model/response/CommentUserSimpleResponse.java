package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.User;
import lombok.Getter;

@Getter
public class CommentUserSimpleResponse {
    private final Long id;
    private final String username;
    private final String name;

    public CommentUserSimpleResponse(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    // User 객체를 UserResponse 객체로 변환하는 메서드
    public static CommentUserSimpleResponse fromUser(User user) {
        return new CommentUserSimpleResponse(user.getId(), user.getUsername(), user.getName());
    }
}
