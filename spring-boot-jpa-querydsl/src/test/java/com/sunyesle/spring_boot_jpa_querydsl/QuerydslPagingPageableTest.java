package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.Member;
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

@DataJpaQuerydslTest
class QuerydslPagingPageableTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        for (int i = 0; i < 25; i++) {
            em.persist(new Member("member" + i, 10 + i));
        }
    }

    @Test
    void pageable() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Member> content = queryFactory
                .selectFrom(member)
                .orderBy(member.age.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member);

        Page<Member> page = PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

        System.out.println("totalPages: " + page.getTotalPages());
        System.out.println("TotalElements: " + page.getTotalElements());
        System.out.println("content: " + page.getContent());
    }
}
