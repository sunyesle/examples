package com.sunyesle.spring_boot_batch.repository;

import com.sunyesle.spring_boot_batch.entity.BeforeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeforeRepository extends JpaRepository<BeforeEntity, Long> {
}
