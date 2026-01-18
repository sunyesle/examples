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
class QuerydslJPQLTest {

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
    }

    @Test
    void JPQL() {
        List<Member> result = em.createQuery(
                        "select m from Member m where m.name = :name"
                        , Member.class)
                .setParameter("name", "member1")
                .getResultList();

        System.out.println(result);
    }

    @Test
    void Querydsl() {
        List<Member> result = queryFactory
                .select(member)
                .from(member)
                .where(member.name.eq("member1"))
                .fetch();

        System.out.println(result);
    }
}
