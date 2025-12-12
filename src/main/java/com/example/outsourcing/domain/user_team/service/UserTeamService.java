package com.example.outsourcing.domain.user_team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.entity.User_Team;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import com.example.outsourcing.domain.user_team.model.request.TeamAddMemberRequest;
import com.example.outsourcing.domain.user_team.model.response.TeamAddMemberResponse;
import com.example.outsourcing.domain.user_team.reposiotry.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTeamService {

    private final UserTeamRepository userTeamRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    //팀 멤버 추가
    @Transactional
    public CommonResponse<TeamAddMemberResponse> addMember(String authority, Long teamId, TeamAddMemberRequest request) {

        if(!UserRole.ADMIN.name().equals(authority)) throw new CustomException(ExceptionCode.FORBIDDEN);

        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM));

        boolean existence = userTeamRepository.existsByUserAndTeam(user, team);

        if(existence) throw new CustomException(ExceptionCode.EXISTS_USER_TEAM);

        User_Team user_team = new User_Team(user, team);

        User_Team savedUserTeam = userTeamRepository.save(user_team);

        TeamAddMemberResponse teamAddMemberResponse = TeamAddMemberResponse.from(savedUserTeam);

        return new CommonResponse<>(true, "팀 멤버가 추가되었습니다.", teamAddMemberResponse);
    }

    //팀 멤버 제거
    @Transactional
    public CommonResponse<Void> deleteMember(String authority, Long teamId, Long userId) {

        if(!UserRole.ADMIN.name().equals(authority)) throw new CustomException(ExceptionCode.FORBIDDEN_DELETE_USER_TEAM);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM_MEMBER));

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM));

        User_Team savedUserTeam = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER_TEAM));

        userTeamRepository.delete(savedUserTeam);

        return new CommonResponse<>(true, "팀 멤버가 제거되었습니다.", null);
    }
}
