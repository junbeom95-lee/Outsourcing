package com.example.outsourcing.domain.activity.model.request;

import com.example.outsourcing.common.enums.ActivityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ActivityRequest {
    private int page = 0;
    private int size = 10;
    private ActivityType type;
    private Long taskId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
