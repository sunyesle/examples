package com.sunyesle.spring_boot_jpa.remove.cascade_type_remove;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<>();

    public Parent() {
    }

    public void addChild(Child child){
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(Child child){
        child.setParent(null);
        children.remove(child);
    }
}
