package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaQuerydslTest
class QuerydslJoinFetchTest {

    @PersistenceUnit
    EntityManagerFactory emf;

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

        em.flush();
        em.clear();
    }

    @Test
    void fetchJoin() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .fetchJoin()
                .fetch();

        for(Member m : result) {
            // Team이 로딩되어 있다.
            assertThat(emf.getPersistenceUnitUtil().isLoaded(m.getTeam())).isTrue();
        }
    }

    @Test
    void noFetchJoin() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .fetch();

        for(Member m : result) {
            // Team이 로딩되어 있지 않다.
            assertThat(emf.getPersistenceUnitUtil().isLoaded(m.getTeam())).isFalse();
        }
    }
}
