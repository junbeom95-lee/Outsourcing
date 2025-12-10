package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    // 팀명 중복 검증
    boolean existsByName(String teamName);


}
