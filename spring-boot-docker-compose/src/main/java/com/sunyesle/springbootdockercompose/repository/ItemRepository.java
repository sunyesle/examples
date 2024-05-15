package com.sunyesle.springbootdockercompose.repository;

import com.sunyesle.springbootdockercompose.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
