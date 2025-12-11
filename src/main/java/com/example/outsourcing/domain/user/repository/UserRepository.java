package com.example.outsourcing.domain.user.repository;

import com.example.outsourcing.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("""
        select u
        from User u
        left join User_Team ut on u = ut.user and ut.team.id = :teamId
        where ut.id is null
        """)
    List<User> findAllUserByUser_idIsNull(@Param("teamId") Long teamId);

    List<User> findAllByNameContaining(String query);
}
