package com.sunyesle.spring_boot_jpa.repository;

import com.sunyesle.spring_boot_jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderItems_Item_Name(String name);
}
