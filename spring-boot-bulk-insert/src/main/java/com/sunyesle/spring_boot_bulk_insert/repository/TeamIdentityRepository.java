package com.sunyesle.spring_boot_bulk_insert.repository;

import com.sunyesle.spring_boot_bulk_insert.entity.TeamIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamIdentityRepository extends JpaRepository<TeamIdentity, Long> {
}
