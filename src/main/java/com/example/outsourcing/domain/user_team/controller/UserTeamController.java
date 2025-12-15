package com.example.outsourcing.domain.user_team.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.auth.model.dto.UserinfoDetails;
import com.example.outsourcing.domain.user_team.model.request.TeamAddMemberRequest;
import com.example.outsourcing.domain.user_team.model.response.TeamAddMemberResponse;
import com.example.outsourcing.domain.user_team.service.UserTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class UserTeamController {

    private final UserTeamService userTeamService;

    //팀 멤버 추가
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{teamId}/members")
    public ResponseEntity<CommonResponse<TeamAddMemberResponse>> addMember(@PathVariable Long teamId,
                                                                           @RequestBody TeamAddMemberRequest request) {

        CommonResponse<TeamAddMemberResponse> response = userTeamService.addMember(teamId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //팀 멤버 제거
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteMember(@PathVariable Long teamId,
                                                             @PathVariable Long userId) {


        CommonResponse<Void> response = userTeamService.deleteMember(teamId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
