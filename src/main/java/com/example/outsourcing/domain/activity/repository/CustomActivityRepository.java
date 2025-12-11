package com.example.outsourcing.domain.activity.repository;

import com.example.outsourcing.common.entity.Activity;
import com.example.outsourcing.domain.activity.model.request.ActivityRequest;
import org.springframework.data.domain.Page;

public interface CustomActivityRepository {

    Page<Activity> search(ActivityRequest request, Long userId);

}
