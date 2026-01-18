package com.sunyesle.spring_boot_jpa_querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunyesle.spring_boot_jpa_querydsl.config.DataJpaQuerydslTest;
import com.sunyesle.spring_boot_jpa_querydsl.entity.TestEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.sunyesle.spring_boot_jpa_querydsl.entity.QTestEntity.testEntity;

@DataJpaQuerydslTest
class QuerydslFunctionTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setup() {
        em.persist(new TestEntity("member1" , 12 , LocalDateTime.now()));
        em.persist(new TestEntity("member2 ", 25 , LocalDateTime.now()));
        em.persist(new TestEntity(null      , -30, LocalDateTime.now()));
    }

    @Test
    void function() {
        List<Tuple> result = queryFactory
                .select(
                        testEntity.str.as("member_name"), // 별칭 지정

                        testEntity.str.lower(),
                        testEntity.str.upper(),
                        testEntity.str.substring(3, 6),
                        testEntity.str.concat("_").concat(testEntity.num.stringValue()),
                        testEntity.str.trim(),
                        testEntity.str.length(),

                        testEntity.num.add(1),
                        testEntity.num.subtract(1),
                        testEntity.num.multiply(2),
                        testEntity.num.divide(2),
                        testEntity.num.mod(10),
                        testEntity.num.abs(),
                        testEntity.num.negate(),
                        testEntity.num.round(),
                        testEntity.num.floor(),
                        testEntity.num.ceil(),

                        testEntity.createdAt.year(),
                        testEntity.createdAt.month(),
                        testEntity.createdAt.dayOfMonth(),

                        testEntity.str.coalesce("default"), // name이 null 이면 default 반환
                        testEntity.str.nullif("member1"), // name이 member1이면 null 반환

                        Expressions.constant("A"),

                        Expressions.currentDate(),
                        Expressions.currentTime(),
                        Expressions.currentTimestamp(),

                        Expressions.stringTemplate("replace({0},{1},{2})", testEntity.str, "member", "M"),
                        Expressions.numberTemplate(Integer.class, "abs({0})", testEntity.num)
                )
                .from(testEntity)
                .fetch();

        System.out.println(result);
    }
}
