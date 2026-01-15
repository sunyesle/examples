package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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
class QuerydslCaseTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        em.persist(new Member("member1", 10, null));
        em.persist(new Member("member2", 20, null));
        em.persist(new Member("member3", 30, null));
        em.persist(new Member("member4", 40, null));
    }

    @Test
    void simpleCase() {
        List<String> result = queryFactory
                .select(
                        member.age
                                .when(10).then("열살")
                                .when(20).then("스무살")
                                .otherwise("기타")
                )
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    void caseBuilder() {
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    void caseBuilderVariable() {
        // CaseBuilder를 변수로 작성하여 재사용할 수 있다.
        NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.age.between(0, 20)).then(2)
                .when(member.age.between(21, 30)).then(1)
                .otherwise(3);

        List<Tuple> result = queryFactory
                .select(
                        rankPath,
                        member.name,
                        member.age
                )
                .from(member)
                .orderBy(rankPath.desc())
                .fetch();

        System.out.println(result);
    }
}
