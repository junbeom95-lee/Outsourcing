package com.example.outsourcing.domain.activity.model.request;

import com.example.outsourcing.common.enums.ActivityType;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ActivityRequest {

    private final int page;
    private final int size;
    private final ActivityType type;
    private final Long taskId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public ActivityRequest(
            Integer page,
            Integer size,
            ActivityType type,
            Long taskId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 10;
        this.type = type;
        this.taskId = taskId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
