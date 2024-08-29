package com.sunyesle.spring_boot_jpa.join2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("SELECT m, t FROM TeamMember m join m.team t")
    List<TeamMember> findAllWithTeamUsingJoin();
}
