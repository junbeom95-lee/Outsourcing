package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    @Query("""
        select t
        from Team t
        left join fetch t.userTeamList ut
        left join fetch ut.user
    """)
    List<Team> findAllWithUsers();
}
