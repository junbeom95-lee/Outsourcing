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
        select t 
        from Team t
        left join fetch User_Team ut on t = ut.team
        left join fetch ut.user u on u = ut.user 
        """)
    List<Team> findAllWithUsers();

}
