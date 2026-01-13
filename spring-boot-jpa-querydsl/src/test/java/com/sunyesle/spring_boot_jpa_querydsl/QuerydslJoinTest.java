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
class QuerydslJoinTest {

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

    // ######################
    // .join(조인 대상, 별칭)
    // ######################

    @Test
    void innerJoin() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .join(member.team, team) // 또는 .innerJoin()
                .fetch();
        for(Tuple tuple : result) {
            System.out.println("tuple: " + tuple);
        }
    }

    @Test
    void leftJoin() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .fetch();
        for(Tuple tuple : result) {
            System.out.println("tuple: " + tuple);
        }
    }

    @Test
    void rightJoin() {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .rightJoin(member.team, team)
                .fetch();
        for(Tuple tuple : result) {
            System.out.println("tuple: " + tuple);
        }
    }
}
