package com.example.outsourcing.domain.task.dto.response;

import com.example.outsourcing.common.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusCountDto {
    private TaskStatus status;
    private Long count;
}
