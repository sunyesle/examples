package com.sunyesle.spring_boot_jpa.remove.orphan_removal;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Child2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent2 parent;

    public Child2() {
    }

    public void setParent(Parent2 parent) {
        this.parent = parent;
    }
}
