package com.example.outsourcing.domain.task.dto.request;

import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskCreateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE")
    private String status;
    @NotBlank
    @Pattern(regexp = "LOW|MEDIUM|HIGH")
    private String priority;
    @NotBlank
    private Long assigneeId;
    @NotBlank
    private LocalDateTime dueDate;

    public TaskStatus getTaskStatus() {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.NOT_MATCHES_STATUS);
        }
    }

    public TaskPriority getTaskPriority() {
        try {
            return TaskPriority.valueOf(priority.toUpperCase());
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.NOT_MATCHES_PRIORITY);
        }
    }

}
