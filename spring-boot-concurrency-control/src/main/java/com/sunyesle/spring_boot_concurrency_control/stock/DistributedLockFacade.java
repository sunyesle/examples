package com.sunyesle.spring_boot_concurrency_control.stock;


import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DistributedLockFacade {
    private final StockService stockService;
    private final RedissonClient redissonClient;

    public void decreaseStock(Long id, int quantity) {
        RLock rLock = redissonClient.getLock("stockLock");
        try {
            //락 획득 요청
            boolean available = rLock.tryLock(5000, 2000, TimeUnit.MILLISECONDS);
            if (!available) {
                //락 획득 실패
                System.out.println("락 획득 실패");
                throw new RuntimeException("LOCK_ACQUISITION_FAILED");
            }

            // 동시성 제어가 필요한 작업 수행
            stockService.decrease(id, quantity);
        } catch (InterruptedException e) {
            // 쓰레드가 인터럽트 될 경우
            throw new RuntimeException("LOCK_INTERRUPTED_ERROR");
        } finally {
            //락 해제
            rLock.unlock();
        }
    }
}
