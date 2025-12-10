package com.example.outsourcing.domain.team.service;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    //팀 목록 조회
    public CommonResponse<List<TeamGetListResponse>> getTeamList() {

        List<Team> teamList = teamRepository.findAllWithUsers();

        List<TeamGetListResponse> teamGetListResponseList = teamList.stream().map(TeamGetListResponse::from).toList();

        return new CommonResponse<>(true, "팀 목록 조회 성공", teamGetListResponseList);
    }
}
