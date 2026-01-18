package com.sunyesle.spring_boot_jpa_querydsl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String str;
    private Integer num;
    private LocalDateTime createdAt;

    public TestEntity(String str, Integer num, LocalDateTime createdAt) {
        this.str = str;
        this.num = num;
        this.createdAt = createdAt;
    }
}
