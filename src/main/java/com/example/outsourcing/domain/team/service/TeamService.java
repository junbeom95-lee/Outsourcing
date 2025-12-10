package com.example.outsourcing.domain.team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetDetailResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetMemberResponse;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user_team.reposiotry.UserTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserTeamRepository userTeamRepository;

    //팀 목록 조회
    @Transactional(readOnly = true)
    public CommonResponse<List<TeamGetListResponse>> getTeamList() {

        List<Team> teamList = teamRepository.findAllWithUsers();

        List<TeamGetListResponse> teamGetListResponseList = teamList.stream().map(TeamGetListResponse::from).toList();

        return new CommonResponse<>(true, "팀 목록 조회 성공", teamGetListResponseList);
    }

    //팀 상세 조회
    @Transactional(readOnly = true)
    public CommonResponse<TeamGetDetailResponse> getTeamDetail(Long id) {

        Team team = teamRepository.findByIdWithUsers(id).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM));

        TeamGetDetailResponse teamGetDetailResponse = TeamGetDetailResponse.from(team);

        return new CommonResponse<>(true, "팀 조회 성공", teamGetDetailResponse);
    }

    //팀 멤버 조회
    @Transactional(readOnly = true)
    public CommonResponse<List<TeamGetMemberResponse>> getTemMemberList(Long teamId) {

        List<User> userList = userTeamRepository.findUsersByTeamId(teamId);

        List<TeamGetMemberResponse> teamGetMemberListResponse = userList.stream()
                .map(TeamGetMemberResponse::from)
                .toList();

        return new CommonResponse<>(true, "팀 멤버 조회 성공", teamGetMemberListResponse);
    }

    //TODO 팀 멤버 추가
    //TODO Param : UserRole role, Long teamId, TeamAddMemberRequest (userId)
    //TODO Data : TeamAddMemberResponse (id, name, description, createdAt, members)
    //TODO members List<> (id, username, name)

    //TODO 팀 멤버 제거
    //TODO Method : DELETE URI : /api/teams/{teamId}/members/{userId}
    //TODO Param : UserRole role, Long teamId, Long userId
    //TODO Data : null
}
