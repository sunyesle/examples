package com.sunyesle.spring_boot_jpa.join;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByDepartmentEmployeesName(String employeeName);
}
