package com.example.outsourcing.domain.team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.team.dto.request.TeamCreateRequest;
import com.example.outsourcing.domain.team.dto.request.TeamUpdateRequest;
import com.example.outsourcing.domain.team.dto.response.TeamCreateResponse;
import com.example.outsourcing.domain.team.dto.response.TeamUpdateResponse;
import com.example.outsourcing.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public ResponseEntity<CommonResponse<TeamCreateResponse>> create(Authentication authentication,
                                                                     @RequestBody @Valid TeamCreateRequest request) {

        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        CommonResponse<TeamCreateResponse> response = teamService.create(authority, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 팀 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TeamUpdateResponse>> update(Authentication authentication,
                                                                     @PathVariable Long id,
                                                                     @RequestBody @Valid TeamUpdateRequest request) {

        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        return ResponseEntity.ok(teamService.update(authority, id, request));  // 뭔가 생각나면 그때 수정
    }
    
    // 팀 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(Authentication authentication,
                                                       @PathVariable Long id) {

        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        return ResponseEntity.ok(teamService.delete(authority, id));
    }

}
