package com.sunyesle.spring_boot_webflux_sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
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

        // 최초 연결 이벤트
        Flux<ServerSentEvent<String>> connectFlux = Flux
                .just(ServerSentEvent.<String>builder()
                        .event("connect")
                        .data("SSE connection established").build());

        // SSE 연결 유지를 위한 heartbeat 이벤트 (30초 간격)
        Flux<ServerSentEvent<String>> heartbeatFlux = Flux
                .interval(Duration.ofSeconds(30))
                .map(i -> ServerSentEvent.<String>builder()
                        .comment("heartbeat").build());

        Flux<ServerSentEvent<String>> messageFlux = sink.asFlux()
                .doOnCancel(() -> {
                    log.info("[SSE] userId={} 연결 취소", userId);
                })
                .doOnError(e -> {
                    log.info("[SSE] userId={} 에러 발생: {}", userId, e.getMessage());
                });

        return Flux.merge(connectFlux, heartbeatFlux, messageFlux)
                .doFinally(signalType -> {
                    log.info("[SSE] userId={} 연결 종료. signal={}", userId, signalType);
                    sinks.remove(userId);
                });
    }

    public void broadcast(SseBroadcastRequest request) {
        sinks.forEach((userId, sink) -> {
            ServerSentEvent<String> event = ServerSentEvent.<String>builder()
                    .event("broadcast")
                    .data(request.message())
                    .build();
            sink.tryEmitNext(event);
        });
    }

    public void send(SseRequest request) {
        Sinks.Many<ServerSentEvent<String>> sink = sinks.get(request.userId());

        if (sink == null) {
            log.info("No connection for userId={}", request.userId());
            return;
        }

        ServerSentEvent<String> event = ServerSentEvent.<String>builder()
                .event("send")
                .data(request.message())
                .build();
        sink.tryEmitNext(event);
    }
}
