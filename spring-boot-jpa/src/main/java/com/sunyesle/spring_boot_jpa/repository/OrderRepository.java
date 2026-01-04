package com.sunyesle.spring_boot_jpa.repository;

import com.sunyesle.spring_boot_jpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderItems_Item_Name(String name);

    @Query("SELECT o FROM Order o JOIN o.member m WHERE m.name = :memberName")
    List<Order> findByMemberNameJPQLJoin(String memberName);

    @Query("SELECT o FROM Order o JOIN FETCH o.member m WHERE m.name = :memberName")
    List<Order> findByMemberNameJPQLFetchJoin(String memberName);
}
