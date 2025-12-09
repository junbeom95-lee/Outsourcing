package com.example.outsourcing.domain.user.model.response;

import com.example.outsourcing.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserGetAvailableResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    public static UserGetAvailableResponse from(User user) {
        return new UserGetAvailableResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                user.getCreatedAt()
        );
    }
}
