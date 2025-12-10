package com.example.outsourcing.domain.team.dto.response;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.domain.user.model.response.UserGetListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamGetResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<UserGetListResponse> members;

//    public static TeamGetResponse from(Team team) {
//        return new TeamGetResponse(
//                team.getId(),
//                team.getName(),
//                team.getDescription(),
//                team.getCreatedAt(),
//
//        );
//    }


    //TODO data List<TeamGetResponse> (id, name, description, createdAt, List<member> members)
    //TODO List<member> (id, username, name, email, role, createdAt)
}
