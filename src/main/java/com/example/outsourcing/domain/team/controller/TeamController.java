package com.example.outsourcing.domain.team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
