package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslSortTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        em.persist(new Member("member1", 10));
        em.persist(new Member("member2", 20));
        em.persist(new Member("member3", 30));
        em.persist(new Member("member4", 40));
        em.persist(new Member(null, 20));
    }

    @Test
    void sort() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(
                        member.age.desc(),
                        member.name.asc().nullsFirst()
                        // asc(), desc()
                        // nullsFirst(), nullsLast()
                )
                .fetch();

        System.out.println(result);
    }
}
