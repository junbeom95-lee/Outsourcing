package com.example.outsourcing.domain.activity.model.response;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ActivityTotalResponse {
    private Long id;
    private ActivityType type;
    private Long userId;
    private ActivityUserResponse user;
    private Long taskId;
    private LocalDateTime timestamp;
    private String description;

    public static ActivityTotalResponse from(Activity activity) {

        ActivityUserResponse activityUserResponse = ActivityUserResponse.from(activity.getUser());

        return new ActivityTotalResponse(
                activity.getId(),
                activity.getType(),
                activity.getUser().getId(),
                activityUserResponse,
                activity.getTaskId(),
                LocalDateTime.now(),
                activity.getDescription()
        );

    }

}
