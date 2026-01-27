package com.sunyesle.spring_boot_bulk_insert.repository;

import com.sunyesle.spring_boot_bulk_insert.entity.TeamUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamUuidRepository extends JpaRepository<TeamUuid, UUID> {
}
