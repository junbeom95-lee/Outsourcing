package com.example.outsourcing.domain.dashboard.service;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.dashboard.dto.DashboardMyTaskDto;
import com.example.outsourcing.domain.dashboard.dto.DashboardStatsResponse;
import com.example.outsourcing.domain.dashboard.dto.DashboardWeelkyResponse;
import com.example.outsourcing.domain.task.dto.response.StatusCountDto;
import com.example.outsourcing.domain.task.dto.response.TaskCountProjection;
import com.example.outsourcing.domain.task.dto.response.TeamTaskCountDto;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.team.repository.TeamRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.Tuple;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    //totalTasks;        // 전체 작업 수
    //completedTasks;    // 완료된 작업 수 (DONE)
    //inProgressTasks;   // 진행 중 작업 수 (IN_PROGRESS)
    // todoTasks;         // 할 일 작업 수 (TODO)
    // overdueTasks;      // 마감 기한 지난 작업 수
    //teamProgress;   // 팀 전체 진행률 (%)
    //completionRate; // 나의 완료율 (%)
    public CommonResponse<DashboardStatsResponse> dashboardStats(Long userId) {

        User assignee = userRepository.findById(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));
        Team usersTeam = teamRepository.findByTeamByUserId(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_TEAM));

        LocalDateTime now = LocalDateTime.now();
        //팀 작업량
        long teamTasks = 0L;
        long doneTeamTasks = 0L;
        //팀 총 카운트 db
        List<TeamTaskCountDto> teamCountList = teamRepository.countTeamTaskGroup(usersTeam.getId());
        //for문으로 done or not 카운트
          for (TeamTaskCountDto list : teamCountList) {
              teamTasks += list.getCount();

              if (TaskStatus.DONE.equals(list.getStatus())) {
                  doneTeamTasks = list.getCount();
              }
          }
        //팀 작업량 백분위
        double teamProgress = Math.round(((float) doneTeamTasks / teamTasks) * 100);
        //토탈 task
        Long completedTasks = 0L;
        Long inProgressTasks = 0L;
        Long todoTasks = 0L;
        //토탈 총 카운트 db
        List<StatusCountDto> countList = taskRepository.countByTasks();
        //for문으로 todo inprogress done 카운트
          for (StatusCountDto list : countList) {
              if (TaskStatus.TODO.equals(list.getStatus())) {
                  todoTasks = list.getCount();
              } else if (TaskStatus.IN_PROGRESS.equals(list.getStatus())) {
                  inProgressTasks = list.getCount();
              } else {
                  completedTasks = list.getCount();
              }
          }
        //총 토탈 카운트
        Long totalTasks = completedTasks + inProgressTasks + todoTasks;
        //기한지난 작업물 db
        Long overdueTasks = taskRepository.countByOverDueDate(now);

        //내 작업량
        double myTotalTask = 0L;
        double myDoneTask = 0L;
        //내 작업량 db
        List<TaskCountProjection> myTaskList = taskRepository.countByMyTasksGrouped(userId);
        for (TaskCountProjection list : myTaskList) {
            myTotalTask += list.getCount();
            if (TaskStatus.DONE.equals(list.getStatus())) {
                myDoneTask = list.getCount();
            }
        }
        double completionRate = 0;
        //내 작업량 백분위
        if (myTotalTask > 0) {
            completionRate = (double) Math.round((myDoneTask / myTotalTask *100));
        }
        return new CommonResponse<>(true,
                "대시보드 통계 조회 성공",
                new DashboardStatsResponse(totalTasks, completedTasks, inProgressTasks, todoTasks, overdueTasks, teamProgress, completionRate)
        );
    }

    public CommonResponse<DashboardMyTaskDto> myTaskSummary(Long userId) {
        User assignee = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));
        LocalDateTime now = LocalDateTime.now();

        List<Task> todos = taskRepository.findTaskByMyStatus(userId, TaskStatus.TODO);
        List<Task> todayTask = taskRepository.findTaskByMyStatus(userId, TaskStatus.IN_PROGRESS);
        //해야할일. 그니까 듀데이트 남은거
        List<Task> upcomingTask = todos.stream()
                .filter(t-> t.getDueDate().isAfter(now) || t.getDueDate().isEqual(now))
                .toList();
        //듀데이트 지난거. 큰일난거.
        List<Task> overdueTask = todos.stream()
                .filter((task -> task.getDueDate().isBefore(now)))
                .toList();

        List<DashboardMyTaskDto.Tasks> todayTasks = todayTask.stream()
                .map(DashboardMyTaskDto.Tasks::new)
                .toList();
        List<DashboardMyTaskDto.Tasks> upcomingTasks = upcomingTask.stream()
                .map(DashboardMyTaskDto.Tasks::new)
                .toList();
        List<DashboardMyTaskDto.Tasks> overdueTasks = overdueTask.stream()
                .map(DashboardMyTaskDto.Tasks::new)
                .toList();

        return new CommonResponse<>(true,"내 작업 요약 조회 성공",new DashboardMyTaskDto(todayTasks, upcomingTasks, overdueTasks));
    }

    public CommonResponse<List<DashboardWeelkyResponse>> weeklyTrend(Long userId) {
        User assignee = userRepository.findById(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));
        LocalDate today = LocalDate.now();
        // 사용할 7개짜리 배열들 생성
        LocalDate[] days = new LocalDate[7];
        LocalDate[] dtoDay = new LocalDate[7];
        String[] dayNames = new String[7];
        int[] tasks = new int[7];
        int[] completed = new int[7];

        List<DashboardWeelkyResponse> dto = new ArrayList<>();

           for (int i = 0; i<7;i++) {
               days[i] = today.plusDays(i);
                dtoDay[i] = today.plusDays(i);
               DayOfWeek dow = days[i].getDayOfWeek();
               String name = switch (dow) {
                   case MONDAY -> "월";
                   case TUESDAY -> "화";
                   case WEDNESDAY -> "수";
                   case THURSDAY -> "목";
                   case FRIDAY -> "금";
                   case SATURDAY -> "토";
                   default -> "일";
               };
               tasks[i] = taskRepository.countTasksDueDate(days[i]);
               completed[i] = taskRepository.countTasksDueDateByStatus(days[i], TaskStatus.DONE);
               dayNames[i] = name;
               dto.add(new DashboardWeelkyResponse(dayNames[i], tasks[i], completed[i], dtoDay[i]));
           }
        return new CommonResponse<>(true,"주간 작업 추세 조회 성공", dto);

    }

}
