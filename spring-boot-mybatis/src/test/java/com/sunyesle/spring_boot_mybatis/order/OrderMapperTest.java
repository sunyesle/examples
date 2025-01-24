package com.sunyesle.spring_boot_mybatis.order;

import com.sunyesle.spring_boot_mybatis.order.vo.OrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void test() {
        OrderVO order1 = new OrderVO(1L, 1000, "1001");
        OrderVO order2 = new OrderVO(1L, 1000, "1002");

        orderMapper.insert(order1);
        orderMapper.insert(order2);

        List<OrderVO> orders = orderMapper.selectAll();

        assertThat(orders).hasSize(2);
        System.out.println(orders);
    }
}
