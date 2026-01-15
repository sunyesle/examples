package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslFunctionTest {

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

        em.persist(new Member("member1", 12, teamA, LocalDateTime.now()));
        em.persist(new Member("member2 ", 25, teamA, LocalDateTime.now()));
        em.persist(new Member(null, -30, teamB, LocalDateTime.now()));
    }

    @Test
    void function() {
        List<Tuple> result = queryFactory
                .select(
                        member.name.as("member_name"), // 별칭 지정

                        member.name.lower(),
                        member.name.upper(),
                        member.name.substring(3, 6),
                        member.name.concat("_").concat(member.age.stringValue()),
                        member.name.trim(),
                        member.name.length(),

                        member.age.add(1),
                        member.age.subtract(1),
                        member.age.multiply(2),
                        member.age.divide(2),
                        member.age.mod(10),
                        member.age.abs(),
                        member.age.negate(),
                        member.age.round(),
                        member.age.floor(),
                        member.age.ceil(),

                        member.createdAt.year(),
                        member.createdAt.month(),
                        member.createdAt.dayOfMonth(),

                        member.name.coalesce("default"), // name이 null 이면 default 반환
                        member.name.nullif("member1"), // name이 member1이면 null 반환

                        Expressions.constant("A"),

                        Expressions.currentDate(),
                        Expressions.currentTime(),
                        Expressions.currentTimestamp(),

                        Expressions.stringTemplate("replace({0},{1},{2})", member.name, "member", "M"),
                        Expressions.numberTemplate(Integer.class, "abs({0})", member.age)
                )
                .from(member)
                .fetch();

        System.out.println(result);
    }
}
