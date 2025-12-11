package com.example.outsourcing.domain.activity.model.response;

import com.example.outsourcing.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivityUserResponse {
    private Long id;
    private String username;
    private String name;

    public static ActivityUserResponse from(User user) {
        return new ActivityUserResponse(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}
