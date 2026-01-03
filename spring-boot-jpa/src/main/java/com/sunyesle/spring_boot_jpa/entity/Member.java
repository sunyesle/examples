package com.sunyesle.spring_boot_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private Integer age;
    private boolean active;
    private LocalDateTime createdAt;

    @Embedded
    private Address address;

    public Member(String name, Integer age, boolean active, LocalDateTime createdAt, Address address) {
        this.name = name;
        this.age = age;
        this.active = active;
        this.createdAt = createdAt;
        this.address = address;
    }
}
