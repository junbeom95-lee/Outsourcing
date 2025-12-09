package com.example.outsourcing.domain.task.dto.response;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
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
    private final User assignee;
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
        task.getAssignee(),
        task.getCreatedAt(),
        task.getUpdatedAt(),
        task.getDueDate()
        );
    }
}
