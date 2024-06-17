package com.sunyesle.spring_boot_concurrency_control.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @Version
    private Long version; // 낙관적 락 사용을 위한 버전 관리 필드

    public Stock(Integer stock) {
        this.stock = stock;
    }

    public void decrease(int quantity) {
        this.stock -= quantity;
    }
}
