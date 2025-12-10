package com.example.outsourcing.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name="team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy="team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User_Team> userTeamList;

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
