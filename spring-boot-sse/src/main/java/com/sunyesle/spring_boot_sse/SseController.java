package com.sunyesle.spring_boot_sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam String userId) {
        log.info("connect - userId={}", userId);
        return sseService.connect(userId);
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody SseBroadcastRequest request) {
        log.info("broadcast - {}", request);
        sseService.broadcast(request);
    }

    @PostMapping("/send")
    public void send(@RequestBody SseRequest request) {
        log.info("send - {}", request);
        sseService.send(request);
    }
}
