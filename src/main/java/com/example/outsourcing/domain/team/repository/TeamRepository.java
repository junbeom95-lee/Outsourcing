package com.example.outsourcing.domain.team.repository;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
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

    // 팀 삭제 시 멤버가 존재하는지
    @Query("""
        select u 
        from User u
        join fetch User_Team ut on ut.user = u 
        where ut.team.id = :teamId
        """)
    List<User> findAllUserByTeamId(@Param("teamId") Long teamId);
}
