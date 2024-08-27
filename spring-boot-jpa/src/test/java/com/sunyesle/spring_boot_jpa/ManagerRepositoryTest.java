package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.join.ManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ManagerRepositoryTest {
    @Autowired
    ManagerRepository employeeRepository;

    @Test
    void test() {
        employeeRepository.findByDepartmentEmployeesName("product name");
    }
}
