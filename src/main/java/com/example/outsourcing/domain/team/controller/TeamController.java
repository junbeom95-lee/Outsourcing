package com.example.outsourcing.domain.team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.dto.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.dto.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.dto.response.TeamCreateResponse;
import com.example.outsourcing.domain.team.dto.response.TeamUpdateResponse;
import com.example.outsourcing.domain.team.service.TeamService;
import com.example.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    // 팀 목록 조회
    //TODO  팀 목록 조회
    //TODO URI : /api/teams, Method : GET
    //TODO data List<TeamGetResponse> (id, name, description, createdAt, List<member> members)
    //TODO List<member> (id, username, name, email, role, createdAt)
    
    
    
    
    // 팀 생성
    // TODO URI : /api/teams, Method : POST
    // TODO Request Body : TeamCreateRequest (name, description)
    // TODO Response data : TeamCreateResponse (id, name, description, createdAt, List<MemberDto> members)
    // TODO List<MemberDto> (id, username, name, email, role, createdAt)
    @PostMapping
    public ResponseEntity<CommonResponse<TeamCreateResponse>> create(@RequestBody @Valid TeamCreateRequest request) {

        CommonResponse<TeamCreateResponse> response = teamService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // 팀 수정
    // TODO URI : /api/teams/{id}, Method : PUT
    // TODO Path Parameter : id (팀 ID)
    // TODO Request Body : TeamUpdateRequest (name, description)
    // TODO Response data : TeamUpdateResponse (id, name, description, createdAt, List<MemberDto> members)
    // TODO List<MemberDto> (id, username, name, email, role, createdAt)
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamUpdateResponse>> update(@AuthenticationPrincipal Long userId,
                                                                     @PathVariable Long id,
                                                                     @RequestBody @Valid TeamUpdateRequest request) {

        return ResponseEntity.ok(teamService.update(userId, id, request));  // 뭔가 생각나면 그때 수정

    }
    
    
    // 팀 삭제
    // TODO URI : /api/teams/{id}, Method : DELETE
    // TODO Path Parameter : id (팀 ID)
    // TODO 성공 시 null, 실패 시 error message
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {

        return ResponseEntity.ok(teamService.delete((id)));
    }

}
