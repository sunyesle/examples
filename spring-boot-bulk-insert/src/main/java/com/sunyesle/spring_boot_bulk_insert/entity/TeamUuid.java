package com.sunyesle.spring_boot_bulk_insert.entity;

import com.sunyesle.spring_boot_bulk_insert.util.UuidUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"members", "isNew"})
@Table(name = "team_uuid")
public class TeamUuid implements Persistable<UUID> {
    @Id
    @Column(name = "team_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<MemberUuid> members = new ArrayList<>();

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() { return isNew; }

    @PostPersist
    @PostLoad
    protected void markNotNew() { this.isNew = false; }

    public TeamUuid(String name) {
        this.id = UuidUtil.generateUuid();
        this.name = name;
    }

    public void addMember(MemberUuid member) {
        members.add(member);
        member.setTeam(this);
    }
}
