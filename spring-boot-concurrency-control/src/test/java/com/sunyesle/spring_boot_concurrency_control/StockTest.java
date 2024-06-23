package com.sunyesle.spring_boot_concurrency_control;

import com.sunyesle.spring_boot_concurrency_control.stock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockTest {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OptimisticLockFacade optimisticLockFacade;

    @Autowired
    private DistributedLockFacade distributeLockFacade;

    @Autowired
    private SynchronizedFacade synchronizedFacade;

    private long STOCK_ID;

    @BeforeEach
    void setUp() {
        stockRepository.deleteAll();
        Stock stock = new Stock(100);
        stockRepository.save(stock);
        STOCK_ID = stock.getId();
    }

    @Test
    void 동시성_이슈가_발생한다() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }

    @Test
    void 비관적_락을_활용한_동시성_제어() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decreaseWithPessimisticLock(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }

    @Test
    void 낙관적_락을_활용한_동시성_제어() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockFacade.decreaseStock(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }

    @Test
    void 분산락을_활용한_동시성_제어() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    distributeLockFacade.decreaseStock(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }

    @Test
    void synchronized를_활용한_동시성_제어() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    synchronizedFacade.decreaseStock(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }

    @Test
    void 분산락을_활용한_동시성_제어_AOP() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decreaseWithDistributedLock(STOCK_ID, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(STOCK_ID).orElseThrow();
        assertThat(stock.getStock()).isZero();
    }
}
