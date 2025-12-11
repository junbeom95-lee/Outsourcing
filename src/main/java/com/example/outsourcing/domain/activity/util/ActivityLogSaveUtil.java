package com.example.outsourcing.domain.activity.util;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.enums.ActivityType;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.domain.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ActivityLogSaveUtil {

    private final ActivityRepository activityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityTaskCreate(Long taskId, Long userId, String title) {

        String message = "새로운 작업 \"" + title  + "\"을 생성했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.TASK_CREATED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityTaskUpdate(Long taskId, Long userId, String title) {

        String message = "작업 \"" +  title + "\" 정보를 수정했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.TASK_UPDATED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityTaskDelete(Long taskId, Long userId, String title) {

        String message = "작업 \"" + title + "\"을 삭제했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.TASK_DELETED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityTaskStatusChange(Long taskId, Long userId, TaskStatus beforeStatus, TaskStatus AfterStatus) {

        String message = "작업 상태를 " + beforeStatus + "에서 " + AfterStatus + "로 변경했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.TASK_STATUS_CHANGED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityCommentCreate(Long taskId, Long userId, String title) {

        String message = "작업 \"" + title + "\"에 댓글을 작성했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.COMMENT_CREATED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityCommentUpdate(Long taskId, Long userId) {

        String message =  "댓글을 수정했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.COMMENT_UPDATED, message);

        activityRepository.save(activity);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityCommentDelete(Long taskId, Long userId) {

        String message = "댓글을 삭제했습니다.";

        Activity activity = new Activity(taskId, userId, ActivityType.COMMENT_CREATED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityUserLogin(Long userId) {

        String message = "로그인 했습니다.";

        Activity activity = new Activity(null, userId, ActivityType.COMMENT_CREATED, message);

        activityRepository.save(activity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveActivityUserLogout(Long userId) {

        String message = "로그아웃 했습니다.";

        Activity activity = new Activity(null, userId, ActivityType.COMMENT_CREATED, message);

        activityRepository.save(activity);

    }
}
