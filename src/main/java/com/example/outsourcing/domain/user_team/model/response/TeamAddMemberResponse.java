package com.example.outsourcing.domain.user_team.model.response;

import com.example.outsourcing.common.entity.User_Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class TeamAddMemberResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<AddMember> members;

    public static TeamAddMemberResponse from(User_Team user_team) {

        AddMember addMember = new AddMember(user_team.getUser().getId(),
                user_team.getUser().getUsername(),
                user_team.getUser().getName());

        List<AddMember> list = List.of(addMember);

        return new TeamAddMemberResponse(
                user_team.getTeam().getId(),
                user_team.getTeam().getName(),
                user_team.getTeam().getDescription(),
                user_team.getTeam().getCreatedAt(),
                list
        );
    }

    @Getter
    @AllArgsConstructor
    private static class AddMember {
        private Long id;
        private String username;
        private String name;
    }
}
