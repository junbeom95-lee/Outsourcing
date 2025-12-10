package com.example.outsourcing.domain.activity.controller;

import com.example.outsourcing.domain.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    //TODO 전체 활동 로그 조회 : getActivities()
    //TODO URI : /api/activities, Method : GET
    //TODO Param : page, size, type, taskId, startDate, endDate
    //TODO Data : ActivityTotalResponse (id, type, userId, user, taskId, timestamp, description)
    //TODO user : (id, username, name)

    //TODO 내 활동 로그 조회 : getMyActivities()
    //TODO URI : /api/activities/me, Method : GET
    //TODO AuthenticationPrincipal Long userId
    //TODO Data : ActivityMyResponse (id, userId, user, action, targetType, targetId, description, createdAt)
    //TODO user : (id, username, name)

}
