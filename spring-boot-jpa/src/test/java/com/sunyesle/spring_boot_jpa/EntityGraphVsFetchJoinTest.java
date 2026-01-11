package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.entity.*;
import com.sunyesle.spring_boot_jpa.repository.MemberRepository;
import com.sunyesle.spring_boot_jpa.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EntityGraphVsFetchJoinTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // 회원(Member) 2명 생성
        Address address = new Address("서울", "테헤란로", "12345");
        Member member1 = new Member("UserA", 25, true, LocalDateTime.now(), address); // 주문이 있는 회원
        Member member2 = new Member("UserB", 28, true, LocalDateTime.now(), address); // 주문이 없는 회원
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 주문(Order) 생성
        Order order1 = new Order(member1, LocalDateTime.now(), OrderStatus.ORDER);
        orderRepository.save(order1);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("@EntityGraph: LEFT OUTER JOIN (변경불가), 모든 회원이 조회된다")
    void entityGraph() {
        List<Member> members = memberRepository.findAllWithEntityGraph();

        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("Fetch Join: INNER JOIN이 기본값, 주문이 있는 회원만 조회된다")
    void fetchJoin() {
        List<Member> members = memberRepository.findAllWithFetchJoin();

        assertThat(members).hasSize(1);
    }

    @Test
    @DisplayName("Fetch Left Join: 명시적 LEFT OUTER JOIN, 모든 회원이 조회된다")
    void fetchJoinLeft() {
        List<Member> members = memberRepository.findAllWithFetchJoinLeft();

        assertThat(members).hasSize(2);
    }
}
