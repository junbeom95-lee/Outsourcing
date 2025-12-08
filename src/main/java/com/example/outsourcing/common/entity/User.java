package com.example.outsourcing.common.entity;

import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.domain.user.model.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;                //유저 고유 ID

    @Column(nullable = false, length = 60, unique = true)
    private String username;        //유저 닉네임

    @Column(nullable = false, length = 100, unique = true)
    private String email;           //이메일

    @Column(nullable = false)
    private String password;        //비밀번호

    @Column(nullable = false)
    private String name;            //유저 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole role;          //권한

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;//삭제일

    public User(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void update(UserUpdateRequest request) {
        this.name = request.getName() == null ? this.name : request.getName() ;
        this.email = request.getEmail() == null ? this.email : request.getEmail();
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

}
