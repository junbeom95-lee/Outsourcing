package com.example.outsourcing.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardStatsResponse {
    private final int totalTasks;        // 전체 작업 수
    private final int completedTasks;    // 완료된 작업 수 (DONE)
    private final int inProgressTasks;   // 진행 중 작업 수 (IN_PROGRESS)
    private final int todoTasks;         // 할 일 작업 수 (TODO)
    private final int overdueTasks;      // 마감 기한 지난 작업 수

    private final double teamProgress;   // 팀 전체 진행률 (%)
    private final double completionRate; // 나의 완료율 (%)
}
