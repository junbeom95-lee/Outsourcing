package com.example.outsourcing.domain.user_team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.user_team.model.request.TeamAddMemberRequest;
import com.example.outsourcing.domain.user_team.model.response.TeamAddMemberResponse;
import com.example.outsourcing.domain.user_team.service.UserTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class UserTeamController {

    private final UserTeamService userTeamService;

    //팀 멤버 추가
    @PostMapping("/{teamId}/members")
    public ResponseEntity<CommonResponse<TeamAddMemberResponse>> addMember(Authentication authentication,
                                                                           @PathVariable Long teamId,
                                                                           @RequestBody TeamAddMemberRequest request) {

        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        CommonResponse<TeamAddMemberResponse> response = userTeamService.addMember(authority, teamId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //팀 멤버 제거
    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteMember(Authentication authentication,
                                                             @PathVariable Long teamId,
                                                             @PathVariable Long userId) {

        String authority = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        CommonResponse<Void> response = userTeamService.deleteMember(authority, teamId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
