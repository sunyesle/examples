package com.sunyesle.spring_boot_webflux_sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
