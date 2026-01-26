package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.dto.MemberSort;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QMember.member;

@DataJpaQuerydslTest
class QuerydslDynamicSortTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        em.persist(new Member("member1", 20));
        em.persist(new Member("member1", 30));
        em.persist(new Member("member2", 25));
        em.persist(new Member("member3", 25));
        em.persist(new Member("member3", 40));
    }

    @Test
    void dynamicSort() {
        List<MemberSort> sortRequests = List.of(MemberSort.NAME_ASC, MemberSort.AGE_DESC);

        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(getSortCondition(sortRequests))
                .fetch();

        System.out.println(result);
    }

    private OrderSpecifier[] getSortCondition(List<MemberSort> sortRequests) {
        // 요청된 정렬 조건 추가
        List<OrderSpecifier<?>> orderSpecifiers = sortRequests.stream()
                .map(this::getOrderSpecifier)
                .collect(Collectors.toList());

        // 기본 정렬 조건 추가
        orderSpecifiers.add(member.id.desc());

        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier<?> getOrderSpecifier(MemberSort sort) {
        return switch (sort) {
            case AGE_ASC -> member.age.asc();
            case AGE_DESC -> member.age.desc();
            case NAME_ASC -> member.name.asc();
            case NAME_DESC -> member.name.desc();
        };
    }
}
