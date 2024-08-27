package com.sunyesle.spring_boot_jpa.join;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<>();

    @OneToOne(mappedBy = "department", fetch = FetchType.LAZY)
    private Manager manager;

    public Department() {
    }

    public Department(String name, List<Employee> employees, Manager manager) {
        this.name = name;
        this.employees = employees;
        this.manager = manager;
    }
}
