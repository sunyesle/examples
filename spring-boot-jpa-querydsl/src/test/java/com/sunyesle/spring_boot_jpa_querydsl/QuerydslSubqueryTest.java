package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.QMember;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslSubqueryTest {

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

        em.persist(new Member("member1", 10, teamA));
        em.persist(new Member("member2", 20, teamA));
        em.persist(new Member("member3", 30, teamB));
        em.persist(new Member("member4", 40, teamB));
    }

    // #############################################################
    // 중첩된 서브쿼리(Nested Subquery) : WHERE 절, HAVING 절의 서브쿼리
    // #############################################################
    @Test
    void whereSubqueryEq() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                                JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub)
                        )
                )
                .fetch();

        System.out.println(result);
    }

    @Test
    void whereSubqueryGoe() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                                JPAExpressions
                                        .select(memberSub.age.avg())
                                        .from(memberSub)
                        )
                )
                .fetch();

        System.out.println(result);
    }

    @Test
    void whereSubqueryIn() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                                JPAExpressions
                                        .select(memberSub.age)
                                        .from(memberSub)
                                        .where(memberSub.age.gt(10))
                        )
                )
                .fetch();

        System.out.println(result);
    }

    @Test
    void whereSubqueryExists() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(
                        JPAExpressions
                                .selectFrom(memberSub)
                                .where(
                                        memberSub.id.eq(member.id),
                                        memberSub.team.name.eq("TeamA"))
                                .exists()
                )
                .fetch();

        System.out.println(result);
    }


    // #############################################################
    // 스칼라 서브쿼리(Scalar Subquery) : SELECT 절의 서브쿼리
    // #############################################################
    @Test
    void selectSubquery() {
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(
                        member.name,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(memberSub.age.avg())
                                        .from(memberSub)
                                , "avg")
                )
                .from(member)
                .fetch();

        System.out.println(result);
    }


    // #############################################################
    // 인라인 뷰(Inline View) : FROM 절의 서브쿼리
    // #############################################################
    // Querydsl은 기본적으로 JPQL의 표준 사양을 따르기 때문에 인라인뷰를 지원하지 않는다.
    // [대안]
    // 1. 서브쿼리를 조인으로 변경한다.
    // 2. 애플리케이션에서 쿼리를 2번으로 나누어 실행한다.
    // 3. Native Query를 사용한다.
}
