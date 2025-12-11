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

    private Long taskId;          //작업 ID

    @Column(nullable = false)
    private Long userId;          //유저 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;  //활동 유형

    @Column(nullable = false)
    private String content;     //변경 내용

    public Activity(Long taskId, Long userId, ActivityType type, String content) {
        this.taskId = taskId;
        this.userId = userId;
        this.type = type;
        this.content = content;
    }
}
