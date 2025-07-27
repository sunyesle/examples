package com.sunyesle.spring_boot_kafka.controller;

import com.sunyesle.spring_boot_kafka.service.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final KafkaMessageProducer kafkaMessageProducer;

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        kafkaMessageProducer.sendMessage(message);
    }
}
