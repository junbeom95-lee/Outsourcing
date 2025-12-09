package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByTeamName(String name);
}
