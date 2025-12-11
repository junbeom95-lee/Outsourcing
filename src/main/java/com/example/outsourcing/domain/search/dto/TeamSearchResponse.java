package com.example.outsourcing.domain.search.dto;

import com.example.outsourcing.common.entity.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TeamSearchResponse {

    private final Long id;
    private final String name;
    private final String description;

    public static TeamSearchResponse from(Team team){
        return new TeamSearchResponse(team.getId(), team.getName(),team.getDescription());}
}
