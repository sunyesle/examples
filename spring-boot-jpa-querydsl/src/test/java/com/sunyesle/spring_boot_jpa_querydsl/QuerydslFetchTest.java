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
class QuerydslFetchTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        Team teamC = new Team("TeamC");
        Team teamD = new Team("TeamD");
        em.persist(teamA);
        em.persist(teamB);
        em.persist(teamC);
        em.persist(teamD);

        em.persist(new Member("member1", 10, teamA));
        em.persist(new Member("member2", 20, teamA));
        em.persist(new Member("member3", 30, teamB));
        em.persist(new Member("member4", 40, null));
        em.persist(new Member("member5", 50, null));
    }

    @Test
    void fetch() {
        // 리스트 조회. 결과가 없으면 빈 리스트를 반환한다.
        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    void fetchOne() {
        // 단건 조회. 결과가 없으면 null을 반환하고, 결과가 여러 건이면 NonUniqueResultException이 발생한다.
        Member result = queryFactory
                .selectFrom(member)
                .where(member.name.eq("member3"))
                .fetchOne();

        System.out.println(result);
    }

    @Test
    void fetchFirst() {
        // 가장 상위 데이터 조회. limit(1).fetchOne()과 동일하다.
        Member result = queryFactory
                .selectFrom(member)
                .fetchFirst();

        System.out.println(result);
    }
}
