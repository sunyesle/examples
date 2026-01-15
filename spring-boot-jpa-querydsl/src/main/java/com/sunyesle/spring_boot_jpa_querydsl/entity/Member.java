package com.sunyesle.spring_boot_jpa_querydsl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"team"})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private Integer age;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name, Integer age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }

    public Member(String name, Integer age, Team team, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.createdAt = createdAt;
    }
}
