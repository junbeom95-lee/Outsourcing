package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.domain.user.model.response.UserGetListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamGetListResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<UserGetListResponse> members;

    public static TeamGetListResponse from(Team team) {

        List<UserGetListResponse> list = team.getUserTeamList()
                .stream()
                .map(userTeam -> UserGetListResponse.from(userTeam.getUser()))
                .toList();

        return new TeamGetListResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                list
        );

    }
}