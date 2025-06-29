package com.sunyesle.spring_boot_sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/sse")
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam String userId) {
        return sseService.connect(userId);
    }

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody SseBroadcastRequest request) {
        sseService.broadcast(request);
    }
}
