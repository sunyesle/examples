package com.sunyesle.spring_boot_kafka.controller;

import com.sunyesle.spring_boot_kafka.dto.MessageDto;
import com.sunyesle.spring_boot_kafka.service.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final KafkaMessageProducer kafkaMessageProducer;

    @PostMapping
    public void sendMessage(@RequestBody MessageDto message) {
        kafkaMessageProducer.sendMessage(message);
    }

    @PostMapping("/with-key/{key}")
    public void sendMessageWithKey(@PathVariable String key, @RequestBody MessageDto message) {
        kafkaMessageProducer.sendMessageWithKey(key, message);
    }

    @PostMapping("/with-partition/{partition}")
    public void sendMessageWithKey(@PathVariable int partition, @RequestBody MessageDto message) {
        kafkaMessageProducer.sendMessageWithPartition(partition, message);
    }
}
