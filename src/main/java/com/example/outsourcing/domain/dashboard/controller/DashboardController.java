package com.example.outsourcing.domain.dashboard.controller;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.dashboard.dto.DashboardMyTaskDto;
import com.example.outsourcing.domain.dashboard.dto.DashboardStatsResponse;
import com.example.outsourcing.domain.dashboard.dto.DashboardWeelkyResponse;
import com.example.outsourcing.domain.dashboard.service.DashboardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@AllArgsConstructor
@Slf4j
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/api/dashboard/stats")
    public ResponseEntity<CommonResponse<DashboardStatsResponse>> dashboardStats(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(dashboardService.dashboardStats(userId));
    }

    @GetMapping("/api/dashboard/tasks")
    public ResponseEntity<CommonResponse<DashboardMyTaskDto>> myTaskSummary(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(dashboardService.myTaskSummary(userId));
    }
    @GetMapping("/api/dashboard/weekly-trend")
    public ResponseEntity<CommonResponse<List<DashboardWeelkyResponse>>> weeklyTrend(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(dashboardService.weeklyTrend(userId));
    }


}
