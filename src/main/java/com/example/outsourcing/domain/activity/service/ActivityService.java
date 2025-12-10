package com.example.outsourcing.domain.activity.service;

import com.example.outsourcing.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    //TODO 전체 활동 로그 조회
    //TODO Param : ActivityQueryRequest (page, size, type, taskId, startDate, endDate)
    //TODO Data : ActivityTotalResponse (id, type, userId, user, taskId, timestamp, description)


    //TODO 내 활동 로그 조회
    //TODO Param Long userId
    //TODO Data : ActivityMyResponse (id, userId, user, action, targetType, targetId, description, createdAt)
}
