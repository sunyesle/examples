package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
class QuerydslDynamicSQLTest {

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


    @Test
    void booleanBuilder() {
        String nameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(nameParam, ageParam);

        System.out.println(result);
    }

    private List<Member> searchMember1(String nameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder(
                // 필수값이 존재하는 경우 생성자에 추가
                // member.name.eq(nameCond)
        );

        if (nameCond != null) {
            builder.and(member.name.eq(nameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }


    @Test
    void booleanExpression() {
        String nameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(nameParam, ageParam);

        System.out.println(result);
    }

    private List<Member> searchMember2(String nameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(nameEq(nameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression nameEq(String nameCond) {
        return nameCond != null ? member.name.eq(nameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }


    @Test
    void booleanExpressionCombine() {
        String nameParam = "member4";

        List<Member> result = searchMember3(nameParam);

        System.out.println(result);
    }

    private List<Member> searchMember3(String nameCond) {
        return queryFactory
                .selectFrom(member)
                .where(isAdult(nameCond))
                .fetch();
    }

    // BooleanExpression 객체를 조립할 수 있다.
    private BooleanExpression isAdult(String nameCond) {
        BooleanExpression isAdultAge = member.age.goe(20);
        BooleanExpression nameCondition = nameEq(nameCond);

        return nameCondition != null ? nameCondition.and(isAdultAge) : isAdultAge;
    }
}
