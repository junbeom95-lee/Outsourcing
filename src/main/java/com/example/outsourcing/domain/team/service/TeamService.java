package com.example.outsourcing.domain.team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.dto.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.dto.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.dto.response.TeamCreateResponse;
import com.example.outsourcing.domain.team.dto.response.TeamUpdateResponse;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

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
    public CommonResponse<TeamUpdateResponse> update(Long userId, Long teamId, @Valid TeamUpdateRequest request) {

        // 작성자 권한 확인


        // 이름 중복 체크
        boolean exitsTeamName = teamRepository.existsByName(request.getName());
        if (exitsTeamName) throw new CustomException(ExceptionCode.EXISTS_TEAM_NAME);

        // 팀 조회 (없으면 에러)
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.TEAM_NOT_FOUND)
        );

        // 팀 정보 수정
        team.update(request.getName(), request.getDescription());

        TeamUpdateResponse response = TeamUpdateResponse.from(team);

        return new CommonResponse<>(true, "팀 정보가 수정되었습니다.", response);

    }



    // 팀 삭제
    @Transactional
    public CommonResponse<Void> delete(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new CustomException(ExceptionCode.TEAM_NOT_FOUND)
        );

        // 팀에 멤버가 있는지 확인은 후에 추가

        // 팀 삭제
        teamRepository.delete(team);

        return new CommonResponse<>(true, "팀이 삭제되었습니다.", null);

    }


}
