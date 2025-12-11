package com.example.outsourcing.domain.task.dto.response;

import com.example.outsourcing.common.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamTaskCountDto {
    private TaskStatus status;
    private Long count;
}
