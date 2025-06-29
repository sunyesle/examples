package com.sunyesle.spring_boot_sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SseService {
    private static final long DEFAULT_TIMEOUT = 1000L * 60 * 10; // SSE emitter 연결 시간, 10분

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(String userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(userId);
        });
        emitter.onError(e -> {
            emitter.completeWithError(e);
            emitters.remove(userId);
        });

        sendEvent(userId, "connect", "SSE connection established");

        return emitter;
    }

    public void broadcast(SseBroadcastRequest request) {
        emitters.forEach((userId, emitter) ->
                sendEvent(userId, "broadcast", request.message())
        );
    }

    public void send(SseRequest request) {
        sendEvent(request.userId(), "send", request.message());
    }

    private void sendEvent(String userId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter == null) {
            return;
        }

        try {
            emitter.send(SseEmitter.event()
                    .name(eventName)
                    .data(data));
        } catch (IOException e) {
            emitter.completeWithError(e);
            emitters.remove(userId);
        }
    }
}
