package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.entity.*;
import com.sunyesle.spring_boot_jpa.repository.ItemRepository;
import com.sunyesle.spring_boot_jpa.repository.MemberRepository;
import com.sunyesle.spring_boot_jpa.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QueryMethodJoinTest {

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

        // 주문(Order) 생성
        Order order = new Order(member, LocalDateTime.now(), OrderStatus.ORDER);

        // 배송 정보(Delivery) 생성
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        order.changeDelivery(delivery);

        // 주문 상품(OrderItem) 생성
        OrderItem orderItem1 = new OrderItem(book, 20000, 2);
        OrderItem orderItem2 = new OrderItem(album, 15000, 1);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        // cascade = CascadeType.ALL로 설정했기 때문에
        // order만 저장해도 delivery와 orderItem들이 함께 저장된다.
        orderRepository.save(order);
    }

    @Test
    @DisplayName("Join: 특정 상품(JPA Book)을 포함한 주문 조회")
    void findByItemName() {
        // 가독성 향상을 위해 연관관계 경계에 언더바를 사용했다.
        List<Order> orders = orderRepository.findByOrderItems_Item_Name("JPA Book");

        assertThat(orders)
                .hasSize(1)
                .allSatisfy(o -> {
                    assertThat(o.getOrderItems())
                            .extracting(oi -> oi.getItem().getName())
                            .contains("JPA Book");
                });
    }
}
