package com.sunyesle.spring_boot_concurrency_control.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockFacade {
    private final StockService stockService;

    public void decreaseStock(Long id, int quantity) {
        while (true) {
            try {
                stockService.decreaseWithOptimisticLock(id, quantity);
                break;
            } catch (Exception e) {
                sleep(50);
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
