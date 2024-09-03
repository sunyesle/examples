package com.sunyesle.spring_boot_jpa.remove.cascade_type_remove;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    public Child() {
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
