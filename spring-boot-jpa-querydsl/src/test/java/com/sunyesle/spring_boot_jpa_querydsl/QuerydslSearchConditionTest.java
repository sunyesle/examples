package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslSearchConditionTest {

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
    @DisplayName("검색 조건")
    void searchCondition() {
        Member result = queryFactory
                .selectFrom(member)
                .where(
                        member.name.eq("member"), // name = 'member'
                        member.name.ne("member"), // name != 'member'
                        member.name.eq("member").not(), // name != 'member'

                        member.name.isNull(), // name is null
                        member.name.isNotNull(), // name is not null

                        member.age.in(10, 20), // age in (10, 20)
                        member.age.notIn(10, 20), // age not in (10, 20)

                        member.age.between(10, 30), // age between 10 and 30

                        member.age.goe(30), // age >= 30
                        member.age.gt(30), // age > 30
                        member.age.loe(30), // age <= 30
                        member.age.lt(30), // age < 30

                        member.name.like("member%"), // name like 'member%'
                        member.name.contains("member"), // name like '%member%'
                        member.name.startsWith("member"), // name like 'member%'
                        member.name.endsWith("member") // name like '%member'
                )
                .fetchOne();

        System.out.println(result);
    }

}
