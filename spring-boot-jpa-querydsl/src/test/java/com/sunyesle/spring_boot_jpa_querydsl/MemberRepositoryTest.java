package com.sunyesle.spring_boot_jpa_querydsl;

import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.dto.MemberSearchCond;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import com.sunyesle.spring_boot_jpa_querydsl.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DataJpaQuerydslTest
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        em.persist(new Member("member1", 10, teamA));
        em.persist(new Member("member2", 20, teamA));
        em.persist(new Member("member3", 30, teamB));
        em.persist(new Member("member4", 40, teamB));
    }

    @Test
    void jpaRepositoryMethod() {
        List<Member> result = memberRepository.findByName("member2");

        System.out.println(result);
    }

    @Test
    void customRepositoryMethod() {
        MemberSearchCond cond = new MemberSearchCond(null, "teamA", 15, 25);

        List<Member> result = memberRepository.search(cond);

        System.out.println(result);
    }
}
