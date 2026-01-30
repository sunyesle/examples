package com.sunyesle.spring_boot_bulk_insert.entity;

import com.sunyesle.spring_boot_bulk_insert.util.UuidUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"team", "isNew"})
@Table(name = "member_uuid")
public class MemberUuid implements Persistable<UUID> {
    @Id
    @Column(name = "member_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamUuid team;

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() { return isNew; }

    @PostPersist
    @PostLoad
    protected void markNotNew() { this.isNew = false; }

    public MemberUuid(String name, Integer age) {
        this.id = UuidUtil.generateUuid();
        this.name = name;
        this.age = age;
    }

    protected void setTeam(TeamUuid team) {
        this.team = team;
    }
}
