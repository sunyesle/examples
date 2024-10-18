package com.sunyesle.spring_boot_kafka.controller;

import com.sunyesle.spring_boot_kafka.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private final MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageProducer.sendMessage("my-topic", message);
        return "Message sent: " + message;
    }
}
