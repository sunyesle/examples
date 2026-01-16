package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;
import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTeam.team;

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

        for (int i = 0; i < 30; i++) {
            em.persist(new Member("member" + i, 10 + i, i % 2 == 0 ? teamA : teamB));
        }
    }

    @Test
    void paging() {
        Pageable pageable = PageRequest.of(0, 10);
        int ageCond = 15;

        List<Tuple> content = queryFactory
                .select(member, team)
                .from(member)
                .join(member.team, team)
                .where(member.age.goe(ageCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(member.age.goe(ageCond));

        Page<Tuple> page = PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

        System.out.println("TotalPages: " + page.getTotalPages());
        System.out.println("getTotalElements: " + page.getTotalElements());
        System.out.println("Content: " + page.getContent());
    }
}
