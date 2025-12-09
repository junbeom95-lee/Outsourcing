package com.example.outsourcing.domain.task.dto.request;

import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TaskUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE")
    private String status;
    @NotBlank
    @Pattern(regexp = "LOW|MEDIUM|HIGH")
    private TaskPriority priority;
    @NotBlank
    private Long assigneeId;
    @NotBlank
    private LocalDateTime dueDate;
}
