package com.sunyesle.spring_boot_concurrency_control.stock;

import com.sunyesle.spring_boot_concurrency_control.redission.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional
    public void decrease(long stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        stock.decrease(quantity);
    }

    @Transactional
    public void decreaseWithPessimisticLock(long stockId, int quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(stockId);
        stock.decrease(quantity);
    }

    @Transactional
    public void decreaseWithOptimisticLock(long stockId, int quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(stockId);
        stock.decrease(quantity);
    }

    @DistributedLock(key = "'stock-' + #stockId")
    public void decreaseWithDistributedLock(long stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        stock.decrease(quantity);
    }
}
