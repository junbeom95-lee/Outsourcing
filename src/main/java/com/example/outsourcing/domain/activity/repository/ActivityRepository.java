package com.example.outsourcing.domain.activity.repository;

import com.example.outsourcing.common.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long>, CustomActivityRepository {
}
