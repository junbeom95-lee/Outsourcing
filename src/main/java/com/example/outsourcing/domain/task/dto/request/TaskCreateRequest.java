package com.example.outsourcing.domain.task.dto.request;

import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @Pattern(regexp = "LOW|MEDIUM|HIGH")
    private String priority;
    @NotNull
    private Long assigneeId;
    @NotNull
    private LocalDateTime dueDate;



    public TaskPriority getTaskPriority() {
        try {
            return TaskPriority.valueOf(priority.toUpperCase());
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.NOT_MATCHES_PRIORITY);
        }
    }

}
