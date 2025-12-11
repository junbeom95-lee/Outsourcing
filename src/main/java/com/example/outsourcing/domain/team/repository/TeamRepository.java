package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    // 팀명 중복 검증
    boolean existsByName(String teamName);

    @Query("""
        select t
        from Team t
        left join fetch t.userTeamList ut
        left join fetch ut.user
    """)
    List<Team> findAllWithUsers();

    @Query("""
        select t
        from Team t
        left join fetch t.userTeamList ut
        left join fetch ut.user
        where t.id = :teamId
    """)
    Optional<Team> findByIdWithUsers(@Param("teamId") Long teamId);

    List<Team> findAllByNameContaining(String query);
}