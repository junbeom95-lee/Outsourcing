package com.example.outsourcing.domain.dashboard.controller;


import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.auth.model.dto.UserinfoDetails;
import com.example.outsourcing.domain.dashboard.model.dto.DashboardMyTaskDto;
import com.example.outsourcing.domain.dashboard.model.response.DashboardStatsResponse;
import com.example.outsourcing.domain.dashboard.model.response.DashboardWeelkyResponse;
import com.example.outsourcing.domain.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<CommonResponse<DashboardStatsResponse>> dashboardStats(@AuthenticationPrincipal UserinfoDetails userDetails) {
        return ResponseEntity.ok(dashboardService.dashboardStats(userDetails.getUserId()));
    }

    @GetMapping("/tasks")
    public ResponseEntity<CommonResponse<DashboardMyTaskDto>> myTaskSummary(@AuthenticationPrincipal UserinfoDetails userDetails) {
        return ResponseEntity.ok(dashboardService.myTaskSummary(userDetails.getUserId()));
    }
    @GetMapping("/weekly-trend")
    public ResponseEntity<CommonResponse<List<DashboardWeelkyResponse>>> weeklyTrend(@AuthenticationPrincipal UserinfoDetails userDetails) {
        return ResponseEntity.ok(dashboardService.weeklyTrend(userDetails.getUserId()));
    }

}
