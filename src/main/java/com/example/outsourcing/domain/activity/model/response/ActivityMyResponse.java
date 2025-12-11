package com.example.outsourcing.domain.activity.model.response;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ActivityMyResponse {
    private Long id;
    private Long userId;
    private ActivityUserResponse user;
    private String action;          //활동 로그 타입 설명
    private ActivityType targetType;//활동 로그 타입
    private Long targetId;          //활동 로그 taskId
    private LocalDateTime timestamp;
    private String description;

    public static ActivityMyResponse from(Activity activity) {

        ActivityUserResponse activityUserResponse = ActivityUserResponse.from(activity.getUser());

        return new ActivityMyResponse(
                activity.getId(),
                activity.getUser().getId(),
                activityUserResponse,
                activity.getType().getAction(),
                activity.getType(),
                activity.getTaskId(),
                activity.getTimestamp(),
                activity.getDescription()
        );

    }
}
