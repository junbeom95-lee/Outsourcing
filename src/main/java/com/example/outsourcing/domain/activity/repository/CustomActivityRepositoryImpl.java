package com.example.outsourcing.domain.activity.repository;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.common.enums.ActivityType;
import com.example.outsourcing.domain.activity.model.request.ActivityRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.outsourcing.common.entity.QActivity.activity;

@Repository
@RequiredArgsConstructor
public class CustomActivityRepositoryImpl implements CustomActivityRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Activity> search(ActivityRequest request, Long userId) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        LocalDateTime start = null;
        LocalDateTime end = null;
        if (request.getStartDate() != null) start = request.getStartDate().atStartOfDay();
        if (request.getEndDate() != null) end = request.getEndDate().plusDays(1).atStartOfDay();


        List<Activity> activityList = jpaQueryFactory.selectFrom(activity)
                .where(
                        typeEq(request.getType()),
                        taskIdEq(request.getTaskId()),
                        dateEq(start, end),
                        userIdEq(userId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(activity.timestamp.desc())
                .fetch();

        Long activityListCount = jpaQueryFactory.select(activity.count())
                .from(activity)
                .where(
                        typeEq(request.getType()),
                        taskIdEq(request.getTaskId()),
                        dateEq(start, end),
                        userIdEq(userId)
                )
                .fetchOne();

        long total = activityListCount != null ? activityListCount : 0L;

        return new PageImpl<>(activityList, pageable, total);
    }

    //활동 유형 확인
    private BooleanExpression typeEq(ActivityType type) {
        return type == null ? null : activity.type.eq(type);
    }

    //작업 아이디 확인
    private BooleanExpression taskIdEq(Long taskId) {
        return taskId == null ? null : activity.taskId.eq(taskId);
    }

    //날짜 확인
    private BooleanExpression dateEq(LocalDateTime startDate, LocalDateTime endDate) {

        if(startDate != null && endDate != null) return activity.timestamp.between(startDate, endDate);
        else if (startDate != null) return activity.timestamp.goe(startDate);
        else if (endDate != null) return activity.timestamp.loe(endDate);
        else return null;
    }

    //아이디 확인
    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : activity.user.id.eq(userId);
    }
}
