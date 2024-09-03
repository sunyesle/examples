package com.sunyesle.spring_boot_jpa.remove.cascade_type_remove;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
