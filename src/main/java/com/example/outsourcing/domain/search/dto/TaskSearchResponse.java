package com.example.outsourcing.domain.search.dto;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.enums.TaskStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TaskSearchResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;

    public static TaskSearchResponse from (Task task){
        return new TaskSearchResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());}
}
