package com.sunyesle.spring_boot_k6.deadlock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/deadlock")
public class DeadlockController {

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    // Lock A를 잡고 1초 대기 후 Lock B를 요청
    @GetMapping("/1")
    public String pipelineOne() {
        log.info("[1] Requesting Lock A...");
        synchronized (lockA) {
            log.info("[1] Acquired Lock A");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            log.info("[1] Requesting Lock B...");
            synchronized (lockB) {
                log.info("[1] Success! Acquired Lock B");
            }
        }
        return "[1] Complete";
    }

    // Lock B를 잡고 1초 대기 후 Lock A를 요청
    @GetMapping("/2")
    public String pipelineTwo() {
        log.info("[2] Requesting Lock B...");
        synchronized (lockB) {
            log.info("[2] Acquired Lock B");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            log.info("[2] Requesting Lock A...");
            synchronized (lockA) {
                log.info("[2] Success! Acquired Lock A");
            }
        }
        return "[2] Complete";
    }
}
