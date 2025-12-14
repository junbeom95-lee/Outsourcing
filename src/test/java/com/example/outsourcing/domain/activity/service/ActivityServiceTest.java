package com.example.outsourcing.domain.activity.service;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ActivityType;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.model.request.ActivityRequest;
import com.example.outsourcing.domain.activity.model.response.ActivityMyResponse;
import com.example.outsourcing.domain.activity.model.response.ActivityTotalResponse;
import com.example.outsourcing.domain.activity.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    private Activity activity;
    private ActivityRequest request;
    private Page<Activity> activityPage;

    @BeforeEach
    void setUp() {
        Long taskId = 1L;
        User user = new User("leeseojun", "lee@seo.jun", "password", "이서준", UserRole.ADMIN);
        ReflectionTestUtils.setField(user, "id", 1L);
        ActivityType type = ActivityType.COMMENT_UPDATED;
        String description = "댓글을 수정했습니다.";
        activity = new Activity(taskId, user, type, description);
        request = new ActivityRequest(0, 10, ActivityType.COMMENT_UPDATED, 1L, LocalDate.now(), LocalDate.now());
        List<Activity> activityList = List.of(activity);
        activityPage = new PageImpl<>(activityList, PageRequest.of(0, 10), activityList.size());
    }


    @Test
    @DisplayName(value = "전체 활동 로그 조회 - 성공 케이스")
    void test_getActivities_success() {

        //given

        when(activityRepository.search(any(ActivityRequest.class), isNull())).thenReturn(activityPage);

        //when
        CommonResponse<PagedModel<ActivityTotalResponse>> response = activityService.getActivities(request);

        //then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("활동 로그 조회 성공");

        PagedModel<ActivityTotalResponse> pagedModel = response.getData();

        ActivityTotalResponse activityTotalResponse = pagedModel.getContent().get(0);
        assertThat(activityTotalResponse.getDescription()).isEqualTo("댓글을 수정했습니다.");
    }

    @Test
    @DisplayName(value = "내 활동 로그 조회 - 성공 케이스")
    void test_getMyActivities_success() {

        //given
        Long userId = 1L;

        //eq() 이 값과 같은 값 (equals)
        when(activityRepository.search(any(ActivityRequest.class), eq(userId))).thenReturn(activityPage);

        //when
        CommonResponse<PagedModel<ActivityMyResponse>> response = activityService.getMyActivities(userId, request);

        //then
        assertThat(response.getMessage()).isEqualTo("내 활동 로그 조회 성공");

        PagedModel<ActivityMyResponse> pagedModel = response.getData();

        ActivityMyResponse activityTotalResponse = pagedModel.getContent().get(0);
        assertThat(activityTotalResponse.getUser().getId()).isEqualTo(userId);
        assertThat(activityTotalResponse.getTargetType()).isEqualTo(ActivityType.COMMENT_UPDATED);
    }
}