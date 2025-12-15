package com.example.outsourcing.domain.team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetDetailResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetListResponse;
import com.example.outsourcing.domain.team.model.response.TeamGetMemberResponse;
import com.example.outsourcing.domain.team.model.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.model.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.model.response.TeamCreateResponse;
import com.example.outsourcing.domain.team.model.response.TeamUpdateResponse;
import com.example.outsourcing.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    // 팀 생성
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<TeamCreateResponse>> create(@RequestBody @Valid TeamCreateRequest request) {

        CommonResponse<TeamCreateResponse> response = teamService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 팀 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamUpdateResponse>> update(@PathVariable Long id,
                                                                     @RequestBody @Valid TeamUpdateRequest request) {

        return ResponseEntity.ok(teamService.update( id, request));  // 뭔가 생각나면 그때 수정
    }

    // 팀 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {

        return ResponseEntity.ok(teamService.delete(id));
    }
}
