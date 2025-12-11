package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamGetDetailResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<TeamGetUserResponse> members;

    public static TeamGetDetailResponse from(Team team) {

        List<TeamGetUserResponse> list = team.getUserTeamList()
                .stream()
                .map(userTeam -> TeamGetUserResponse.from(userTeam.getUser()))
                .toList();

        return new TeamGetDetailResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                list
        );
    }

    @Getter
    @AllArgsConstructor
    private static class TeamGetUserResponse {
        private Long id;
        private String username;
        private String name;
        private String email;
        private UserRole role;

        private static TeamGetUserResponse from(User user) {
            return new TeamGetUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
    }
}
