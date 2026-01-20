package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslPagingTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        for (int i = 0; i < 25; i++) {
            em.persist(new Member("member" + i, 10 + i, i % 2 == 0 ? teamA : teamB));
        }
    }

    @Test
    void paging() {
        int offset = 0;
        int limit = 10;

        List<Member> content = queryFactory
                .selectFrom(member)
                .orderBy(member.age.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        Long count = queryFactory
                .select(member.count())
                .from(member)
                .fetchOne();

        System.out.println("count: " + count);
        System.out.println("content: " + content);
    }
}
