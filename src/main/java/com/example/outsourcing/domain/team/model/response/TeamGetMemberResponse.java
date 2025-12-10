package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TeamGetMemberResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;

    public static TeamGetMemberResponse from(User user) {
        return new TeamGetMemberResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
