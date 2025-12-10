package com.example.outsourcing.domain.team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetDetailResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetMemberResponse;
import com.example.outsourcing.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    //팀 목록 조회
    @GetMapping()
    public ResponseEntity<CommonResponse<List<TeamGetListResponse>>> getTeamList() {

        CommonResponse<List<TeamGetListResponse>> response = teamService.getTeamList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //팀 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamGetDetailResponse>> getTeamDetail(@PathVariable Long id) {

        CommonResponse<TeamGetDetailResponse> response = teamService.getTeamDetail(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //팀 멤버 조회
    @GetMapping("/{teamId}/members")
    public ResponseEntity<CommonResponse<List<TeamGetMemberResponse>>> getTeamMemberList(@PathVariable Long teamId) {

        CommonResponse<List<TeamGetMemberResponse>> response = teamService.getTemMemberList(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
