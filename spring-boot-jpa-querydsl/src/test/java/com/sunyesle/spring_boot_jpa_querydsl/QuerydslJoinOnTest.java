package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
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
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTeam.team;

@DataJpaQuerydslTest
class QuerydslJoinOnTest {

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
        em.persist(new Member("TeamA", 20, null));
        em.persist(new Member("TeamB", 20, null));
    }

    // ######################
    // on 절 사용 예시
    // ######################

    // 1. 연관 관계가 없는 엔티티 간의 조인 (Theta Join)
    @Test
    void thetaJoin() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.name.eq(team.name))
                .fetch();

        System.out.println(result);
    }

    // 2. 조인 대상을 필터링할 때
    // 외부 조인 시에는 on 절에 필터링 조건을 넣으면 조인 대상 테이블의 데이터만 걸러지고, 기준 테이블은 유지된다.
    // 내부 조인 시에는 where 절에서 필터링하는 것과 동일하므로, 가독성을 위해 where을 선호한다.
    @Test
    void filtering() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("TeamA")) // 외부조인. 특정 조건을 가진 데이터만 조인.
                .fetch();

        System.out.println(result);
    }
}
