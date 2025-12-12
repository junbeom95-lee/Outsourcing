package com.example.outsourcing.domain.dashboard.dto;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.domain.user.model.response.UserGetOneResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardMyTaskDto {
    List<Tasks> todayTasks;
    List<Tasks> upcomingTasks;
    List<Tasks> overdueTasks;

    @Getter
    public static class Tasks {
        private final Long id;
        private final UserGetOneResponse assignee;
        private final String title;
        private final TaskStatus status;
        private final TaskPriority priority;
        private final LocalDateTime dueDate;
        public Tasks(Task todayTasks) {
            this.id = todayTasks.getId();
            this.assignee = UserGetOneResponse.from(todayTasks.getAssignee());
            this.title = todayTasks.getTitle();
            this.status = todayTasks.getStatus();
            this.priority = todayTasks.getPriority();
            this.dueDate = todayTasks.getDueDate();
        }
    }

}
