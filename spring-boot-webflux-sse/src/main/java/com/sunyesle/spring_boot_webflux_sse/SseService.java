package com.sunyesle.spring_boot_webflux_sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    private final Map<String, Sinks.Many<ServerSentEvent<String>>> sinks = new ConcurrentHashMap<>();

    public Flux<ServerSentEvent<String>> connect(String userId) {
        Sinks.Many<ServerSentEvent<String>> sink =
                sinks.computeIfAbsent(userId, id -> Sinks.many().multicast().onBackpressureBuffer());

        log.info("User connected userId={}", userId);

        return sink.asFlux()
                .doOnCancel(() -> {
                    log.info("[SSE] userId={} 연결 취소", userId);
                })
                .doOnError(e -> {
                    log.info("[SSE] userId={} 에러 발생: {}", userId, e.getMessage());
                })
                .doFinally(signalType -> {
                    log.info("[SSE] userId={} 연결 종료. signal={}", userId, signalType);
                    sinks.remove(userId);
                });
    }
}
