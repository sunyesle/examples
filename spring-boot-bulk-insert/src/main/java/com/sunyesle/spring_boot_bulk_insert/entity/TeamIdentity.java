package com.sunyesle.spring_boot_bulk_insert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"members"})
@Table(name = "team_identity")
public class TeamIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<MemberIdentity> members = new ArrayList<>();

    public TeamIdentity(String name) {
        this.name = name;
    }

    public void addMember(MemberIdentity member) {
        members.add(member);
        member.setTeam(this);
    }
}
