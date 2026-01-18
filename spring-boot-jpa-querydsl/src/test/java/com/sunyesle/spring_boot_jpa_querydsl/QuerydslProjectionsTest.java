package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.dto.*;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import com.sunyesle.spring_boot_jpa_querydsl.entity.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslProjectionsTest {

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

    // #############################################################
    // Projection
    // #############################################################

    @Test
    @DisplayName("프로젝션 대상이 하나일 때")
    void singleProjection() {
        List<String> result = queryFactory
                .select(member.name)
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("프로젝션 대상이 하나일 때 distinct")
    void distinctSingleProjection() {
        List<String> result = queryFactory
                .select(member.name).distinct()
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("프로젝션 대상이 두개 이상일 때")
    void multipleProjection() {
        List<Tuple> result = queryFactory
                .select(
                        member.name,
                        member.age)
                .from(member)
                .fetch();

        System.out.println(result);
    }


    // #############################################################
    // DTO Projection
    // #############################################################

    @Test
    @DisplayName("JPQL에서 DTO 조회")
    void JPQLDtoProjection() {
        List<MemberDtoConstructor> result = em.createQuery(
                        "select new com.sunyesle.spring_boot_jpa_querydsl.dto.MemberDtoConstructor(m.name, m.age) "
                                + "from Member m"
                        , MemberDtoConstructor.class)
                .getResultList();

        System.out.println(result);
    }

    @Test
    @DisplayName("1. @QueryProjection 활용")
    void queryProjection() {
        List<MemberDtoProjection> result = queryFactory
                .select(new QMemberDtoProjection(member.name, member.age))
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("2. 생성자 사용")
    void constructor() {
        List<MemberDtoConstructor> result = queryFactory
                .select(Projections.constructor(MemberDtoConstructor.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("3. Setter 사용")
    void bean() {
        List<MemberDtoBean> result = queryFactory
                .select(Projections.bean(MemberDtoBean.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("4. 필드 직접 접근. 리플랙션 활용")
    void fields() {
        List<MemberDtoFields> result = queryFactory
                .select(Projections.fields(MemberDtoFields.class,
                        member.name,
                        member.age))
                .from(member)
                .fetch();

        System.out.println(result);
    }

    @Test
    @DisplayName("별칭이 다를 때")
    void test6() {
        QMember memberSub = new QMember("memberSub");

        // fields, bean을 사용하는 경우 쿼리 결과의 별칭과 DTO의 필드명이 정확히 일치해야 한다.
        // 이름이 다를 경우 .as("alias") 또는 ExpressionUtils.as(..., "alias")를 사용하여 별칭을 지정해야 한다.
        List<UserDto> result = queryFactory
                .select(
                        Projections.fields(
                                UserDto.class,
                                member.name.as("username"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(memberSub.age.max())
                                                .from(memberSub), "age"
                                )
                        )
                )
                .from(member)
                .fetch();

        System.out.println(result);
    }
}
