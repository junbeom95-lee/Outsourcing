package com.example.outsourcing.domain.activity.service;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.model.request.ActivityRequest;
import com.example.outsourcing.domain.activity.model.response.ActivityMyResponse;
import com.example.outsourcing.domain.activity.model.response.ActivityTotalResponse;
import com.example.outsourcing.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    //전체 활동 로그 조회
    @Transactional(readOnly = true)
    public CommonResponse<PagedModel<ActivityTotalResponse>> getActivities(ActivityRequest request) {

        Page<Activity> activityPage = activityRepository.search(request, null);

        Page<ActivityTotalResponse> activityTotalResponsePage = activityPage.map(ActivityTotalResponse::from);

        PagedModel<ActivityTotalResponse> pagedModel = new PagedModel<>(activityTotalResponsePage);

        return new CommonResponse<>(true, "활동 로그 조회 성공", pagedModel);
    }

    //내 활동 로그 조회
    @Transactional(readOnly = true)
    public CommonResponse<PagedModel<ActivityMyResponse>> getMyActivities(Long userId, ActivityRequest request) {

        Page<Activity> activityPage = activityRepository.search(request, userId);

        Page<ActivityMyResponse> activityMyResponse = activityPage.map(ActivityMyResponse::from);

        PagedModel<ActivityMyResponse> pagedModel = new PagedModel<>(activityMyResponse);

        return new CommonResponse<>(true, "내 활동 로그 조회 성공", pagedModel);
    }
}
