package com.sunyesle.spring_boot_sse;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SseScheduler {
    private final SseService sseService;

    @Scheduled(fixedRate = 30000)
    public void sendHeartbeat() {
        sseService.sendHeartbeat();
    }
}
