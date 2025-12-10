package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamUpdateResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<Object> members;

    public static TeamUpdateResponse from(Team team) {
        return new TeamUpdateResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                new ArrayList<>()
        );
    }

}
