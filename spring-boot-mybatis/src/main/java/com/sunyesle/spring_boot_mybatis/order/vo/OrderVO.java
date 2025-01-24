package com.sunyesle.spring_boot_mybatis.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private Long orderId;
    private Long memberId;
    private Integer totalAmount;
    private String orderStatus;

    public OrderVO(long memberId, int totalAmount, String orderStatus) {
        this.memberId = memberId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }
}
