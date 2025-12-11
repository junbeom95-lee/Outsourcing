package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.domain.task.dto.response.TeamTaskCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

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

        @Query("""
        select task.status, count(task)
        from Team t
        join t.userTeamList ut
        join ut.user u
        join u.taskList task
        where t.id = :teamId
        group by task.status
        """)
        List<TeamTaskCountDto> countTeamTaskGroup(@Param("teamId") Long teamId);


        @Query("select t from Team t join fetch t.userTeamList ut left join fetch ut.user u where u.id =:userId")
        Optional<Team> findByTeamByUserId(@Param("userId")Long userId);
}