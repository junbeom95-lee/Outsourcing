package com.example.outsourcing.domain.task.dto.response;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponse {

    private final Long id;
    private final String title;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Long assigneeId;
    private final Object assignee;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee().getId(),
                new AssigneeBasic(task.getAssignee()),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }

    public static TaskResponse fromDetail(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee().getId(),
                new AssigneeDetail(task.getAssignee()),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }

    @Getter
    private static class AssigneeBasic {
        private final Long id;
        private final String username;
        private final String name;

        private AssigneeBasic(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.name = user.getName();
        }
    }

    @Getter
    private static class AssigneeDetail {
        private final Long id;
        private final String username;
        private final String name;
        private final String email;

        private AssigneeDetail(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.name = user.getName();
            this.email = user.getEmail();
        }
    }
}
