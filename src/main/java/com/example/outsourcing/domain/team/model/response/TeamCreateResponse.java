package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.domain.user.model.response.UserGetListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamCreateResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<UserGetListResponse> members;

    public static TeamCreateResponse from(Team team) {
        return new TeamCreateResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                new ArrayList<>()
        );
    }


}
