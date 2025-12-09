package com.example.outsourcing.domain.task.dto.request;

import com.example.outsourcing.common.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskStatusChangeRequest {
    private TaskStatus taskStatus;
}
