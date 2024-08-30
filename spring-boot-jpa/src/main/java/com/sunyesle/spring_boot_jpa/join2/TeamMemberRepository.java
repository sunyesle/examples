package com.sunyesle.spring_boot_jpa.join2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("SELECT m FROM TeamMember m JOIN m.team t")
    List<TeamMember> findAllWithTeamUsingJoin();

    @Query("SELECT m, t FROM TeamMember m JOIN m.team t")
    List<TeamMember> findAllWithTeamUsingJoinSelect();

    @Query("SELECT m FROM TeamMember m JOIN FETCH m.team t")
    List<TeamMember> findAllWithTeamUsingFetchJoin();
}
