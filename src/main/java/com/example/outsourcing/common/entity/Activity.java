package com.example.outsourcing.common.entity;

import com.example.outsourcing.common.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "activity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;            //활동 로그 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id",nullable = false)
    private Task task;          //작업 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;          //유저 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;  //활동 유형

    @Column(nullable = false)
    private String content;     //변경 내용

    public Activity(Task task, User user, ActivityType type, String content) {
        this.task = task;
        this.user = user;
        this.type = type;
        this.content = content;
    }
}
