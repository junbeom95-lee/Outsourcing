package com.example.outsourcing.domain.team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetDetailResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetMemberResponse;
import com.example.outsourcing.domain.team.model.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.model.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.model.response.TeamCreateResponse;
import com.example.outsourcing.domain.team.model.response.TeamUpdateResponse;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user_team.reposiotry.UserTeamRepository;
import jakarta.validation.Valid;
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

    // 팀 생성
    @Transactional
    public CommonResponse<TeamCreateResponse> create(TeamCreateRequest request) {

        // 팀 이름 중복 체크
        boolean exitsTeamName = teamRepository.existsByName(request.getName());
        // 중복된 팀 이름 예외 발생
        if (exitsTeamName) {
            throw new CustomException(ExceptionCode.EXISTS_TEAM_NAME);
        }

        // 팀 엔티티 생성
        Team team = new Team(request.getName(), request.getDescription());
        // DB 에 저장
        Team savedTeam = teamRepository.save(team);
        // DTO 변환
        TeamCreateResponse response = TeamCreateResponse.from(savedTeam);
        // CommonResponse 반환
        return new CommonResponse<>(true, "팀이 생성되었습니다.", response);
    }


    // 팀 수정
    @Transactional
    public CommonResponse<TeamUpdateResponse> update(Long teamId, @Valid TeamUpdateRequest request) {

        // 이름 중복 체크
        boolean exitsTeamName = teamRepository.existsByName(request.getName());
        if (exitsTeamName) throw new CustomException(ExceptionCode.EXISTS_TEAM_NAME);

        // 팀 조회 (없으면 에러)
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM)
        );

        // 팀 정보 수정
        team.update(request.getName(), request.getDescription());

        List<User> members = teamRepository.findAllUserByTeamId(teamId);

        TeamUpdateResponse response = TeamUpdateResponse.from(team, members);


        return new CommonResponse<>(true, "팀 정보가 수정되었습니다.", response);

    }



    // 팀 삭제
    @Transactional
    public CommonResponse<Void> delete(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TEAM)
        );

        // 팀에 멤버가 있는지 확인은 후에 추가
        List<User> members = teamRepository.findAllUserByTeamId(teamId);

        if (!members.isEmpty()) {
            throw new CustomException(ExceptionCode.TEAM_HAS_MEMBER);
        }

        // 팀 삭제
        teamRepository.delete(team);

        return new CommonResponse<>(true, "팀이 삭제되었습니다.", null);

    }


}
