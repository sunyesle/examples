package com.sunyesle.spring_boot_bulk_insert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"team"})
@Table(name = "member_identity")
public class MemberIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamIdentity team;

    public MemberIdentity(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    protected void setTeam(TeamIdentity team) {
        this.team = team;
    }
}
