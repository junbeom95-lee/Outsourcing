package com.example.outsourcing.domain.task.dto.request;

import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.exception.CustomException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskStatusChangeRequest {
    @NotBlank
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE")
    private String status;
    public TaskStatus getTaskStatus() {
        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.NOT_MATCHES_STATUS);
        }
    }
}
