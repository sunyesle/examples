package com.sunyesle.spring_boot_webflux_sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    @GetMapping("/connect")
    public Flux<ServerSentEvent<String>> connect(@RequestParam String userId) {
        return sseService.connect(userId);
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody SseBroadcastRequest request) {
        sseService.broadcast(request);
    }

    @PostMapping("/send")
    public void send(@RequestBody SseRequest request) {
        sseService.send(request);
    }
}
