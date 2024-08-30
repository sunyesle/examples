package com.sunyesle.spring_boot_jpa.join2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t JOIN t.members")
    List<Team> findAllWithMemberUsingJoin();

    @Query("SELECT t FROM Team t JOIN FETCH t.members")
    List<Team> findAllWithMemberUsingFetchJoin();
}
