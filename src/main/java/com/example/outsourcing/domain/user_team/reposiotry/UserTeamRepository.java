package com.example.outsourcing.domain.user_team.reposiotry;

import com.example.outsourcing.common.entity.Team;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.entity.User_Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTeamRepository extends JpaRepository<User_Team, Long> {

    @Query("""
        select u
        from User u
        left join User_Team ut on u = ut.user and ut.team.id = :teamId
        where ut.id is null
        """)
    List<User> findAllUserByUser_idIsNull(@Param("teamId") Long teamId);

    @Query("""
        select ut.user
        from User_Team ut
        join ut.team t on ut.team = t
        where t.id = :teamId
    """)
    List<User> findUsersByTeamId(@Param("teamId") Long teamId);


    boolean existsByUserAndTeam(User user, Team team);
}
