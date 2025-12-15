package com.example.outsourcing.domain.activity.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.model.request.ActivityRequest;
import com.example.outsourcing.domain.activity.model.response.ActivityMyResponse;
import com.example.outsourcing.domain.activity.model.response.ActivityTotalResponse;
import com.example.outsourcing.domain.activity.service.ActivityService;
import com.example.outsourcing.domain.auth.model.dto.UserinfoDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    //전체 활동 로그 조회
    @GetMapping()
    public ResponseEntity<CommonResponse<PagedModel<ActivityTotalResponse>>> getActivities(@ModelAttribute ActivityRequest request) {

        CommonResponse<PagedModel<ActivityTotalResponse>> response = activityService.getActivities(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //내 활동 로그 조회
    @GetMapping("/me")
    public ResponseEntity<CommonResponse<PagedModel<ActivityMyResponse>>> getMyActivities(@AuthenticationPrincipal UserinfoDetails userinfoDetails,
                                                                                          @ModelAttribute ActivityRequest request) {

        CommonResponse<PagedModel<ActivityMyResponse>> response = activityService.getMyActivities(userinfoDetails.getUserId(), request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
