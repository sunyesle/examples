package com.sunyesle.spring_boot_jpa.remove.orphan_removal;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Parent2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Child2> children = new ArrayList<>();

    public Parent2() {
    }

    public void addChild(Child2 child){
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(Child2 child){
        child.setParent(null);
        children.remove(child);
    }
}
