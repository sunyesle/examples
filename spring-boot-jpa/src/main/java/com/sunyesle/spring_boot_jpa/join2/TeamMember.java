package com.sunyesle.spring_boot_jpa.join2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "team")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamMember() {
    }

    public TeamMember(String name) {
        this.name = name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
