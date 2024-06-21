package com.sunyesle.spring_boot_concurrency_control.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SynchronizedFacade {
    private final StockService stockService;

    public synchronized void decreaseStock(Long id, int quantity) {
        stockService.decrease(id, quantity);
    }
}
