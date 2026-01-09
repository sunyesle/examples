package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.entity.*;
import com.sunyesle.spring_boot_jpa.repository.ItemRepository;
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
class JPQLJoinTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        // 회원(Member) 생성
        Address address = new Address("서울", "테헤란로", "12345");
        Member member = new Member("UserA", 25, true, LocalDateTime.now(), address);
        memberRepository.save(member);

        // 상품(Item) 생성
        Item book = new Item("JPA Book", 20000, 100);
        Item album = new Item("Spring Album", 15000, 50);
        itemRepository.saveAll(List.of(book, album));

        // 주문(Order) 2개 생성
        Order order1 = new Order(member, LocalDateTime.now(), OrderStatus.ORDER);
        Order order2 = new Order(member, LocalDateTime.now(), OrderStatus.CANCEL);

        // 배송 정보(Delivery) 생성
        Delivery delivery1 = new Delivery(address, DeliveryStatus.COMP);
        Delivery delivery2 = new Delivery(address, DeliveryStatus.READY);
        order1.setDelivery(delivery1);
        order2.setDelivery(delivery2);

        // 주문 상품(OrderItem) 생성
        OrderItem orderItem1 = new OrderItem(book, 20000, 2);
        OrderItem orderItem2 = new OrderItem(album, 15000, 1);
        OrderItem orderItem3 = new OrderItem(album, 15000, 2);
        order1.addOrderItem(orderItem1);
        order1.addOrderItem(orderItem2);
        order2.addOrderItem(orderItem3);

        // cascade = CascadeType.ALL로 설정했기 때문에
        // order만 저장해도 delivery와 orderItem들이 함께 저장된다.
        orderRepository.save(order1);
        orderRepository.save(order2);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("JPQL 일반 Join: Member 접근 시 추가 쿼리 발생 (N+1 문제)")
    void join() {
        // 일반 Join : join 조건을 제외하고, 실제 질의하는 대상 Entity의 컬럼만 SELECT 한다.
        List<Order> orders = orderRepository.findByMemberNameJPQLJoin("UserA");

        assertThat(orders).isNotEmpty();
        Order order = orders.get(0);

        System.out.println("--- Member 접근 전 ---");
        // Member가 로딩 되어있지 않다.
        boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(order.getMember());
        assertThat(isLoaded).isFalse();

        // Member를 조회하기 위한 추가 쿼리 로그가 찍힌다.
        assertThat(order.getMember().getName()).isEqualTo("UserA");
        System.out.println("--- Member 접근 후 ---");
    }

    @Test
    @DisplayName("JPQL Fetch Join: 한 번의 쿼리로 Member까지 조회")
    void fetchJoin() {
        // FetchJoin : 실제 질의하는 대상 Entity와 Fetch join이 걸려있는 Entity의 컬럼을 함께 SELECT 한다.
        List<Order> orders = orderRepository.findByMemberNameJPQLFetchJoin("UserA");

        assertThat(orders).isNotEmpty();
        Order order = orders.get(0);

        System.out.println("--- Member 접근 전 ---");
        // Member가 로딩 되어있다.
        boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(order.getMember());
        assertThat(isLoaded).isTrue();

        // 쿼리 로그가 찍히지 않는다.
        assertThat(order.getMember().getName()).isEqualTo("UserA");
        System.out.println("--- Member 접근 후 ---");
    }
}
