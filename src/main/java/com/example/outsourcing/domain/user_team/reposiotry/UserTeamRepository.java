package com.example.outsourcing.domain.user_team.reposiotry;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.entity.User_Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<User_Team, Long> {

    @Query("""
        select ut.user
        from User_Team ut
        join ut.team t on ut.team = t
        where t.id = :teamId
    """)
    List<User> findUsersByTeamId(@Param("teamId") Long teamId);

    boolean existsByUserAndTeam(User user, Team team);

    Optional<User_Team> findByUserAndTeam(User user, Team team);
}
