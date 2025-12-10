package com.example.outsourcing.domain.team.model.response;

import com.example.outsourcing.common.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamResponse {

    private Long id;
    private String name;
    private String description;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
    }

}
