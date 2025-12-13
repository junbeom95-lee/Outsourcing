package com.example.outsourcing.domain.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//totalTasks;        // 전체 작업 수
//completedTasks;    // 완료된 작업 수 (DONE)
//inProgressTasks;   // 진행 중 작업 수 (IN_PROGRESS)
// todoTasks;         // 할 일 작업 수 (TODO)
// overdueTasks;      // 마감 기한 지난 작업 수
//teamProgress;   // 팀 전체 진행률 (%)
//completionRate; // 나의 완료율 (%)
public class DashboardStatsResponse {
    private final Long totalTasks;        // 전체 작업 수
    private final Long completedTasks;    // 완료된 작업 수 (DONE)
    private final Long inProgressTasks;   // 진행 중 작업 수 (IN_PROGRESS)
    private final Long todoTasks;         // 할 일 작업 수 (TODO)
    private final Long overdueTasks;      // 마감 기한 지난 작업 수

    private final double teamProgress;   // 팀 전체 진행률 (%)
    private final double completionRate; // 나의 완료율 (%)
}
