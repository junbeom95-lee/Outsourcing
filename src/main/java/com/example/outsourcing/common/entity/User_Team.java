package com.example.outsourcing.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_team",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_team",
                        columnNames = {"user_id", "team_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User_Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_team_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id",nullable = false)
    private Team team;

    public User_Team(User user, Team team) {
        this.user = user;
        this.team = team;
    }
}
