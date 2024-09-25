package com.sunyesle.spring_boot_batch.repository;

import com.sunyesle.spring_boot_batch.entity.AfterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfterRepository extends JpaRepository<AfterEntity, Long> {
}
