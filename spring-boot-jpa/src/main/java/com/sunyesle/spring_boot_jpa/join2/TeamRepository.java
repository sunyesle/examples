package com.sunyesle.spring_boot_jpa.join2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT distinct t FROM Team t join t.members")
    List<Team> findAllWithMemberUsingJoin();

    @Query("SELECT distinct t FROM Team t join fetch t.members")
    List<Team> findAllWithMemberUsingFetchJoin();
}
