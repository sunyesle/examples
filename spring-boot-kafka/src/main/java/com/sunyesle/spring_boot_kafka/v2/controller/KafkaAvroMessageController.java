package com.sunyesle.spring_boot_kafka.v2.controller;

import com.sunyesle.spring_boot_kafka.avro.MessageEvent;
import com.sunyesle.spring_boot_kafka.v2.service.KafkaAvroMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Profile("kafka-schema-registry")
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class KafkaAvroMessageController {

    private final KafkaAvroMessageProducer kafkaMessageProducer;

    @PostMapping
    public void sendMessage(@RequestBody MessageEvent message) {
        kafkaMessageProducer.sendMessage(message);
    }

    @PostMapping("/with-key/{key}")
    public void sendMessageWithKey(@PathVariable String key, @RequestBody MessageEvent message) {
        kafkaMessageProducer.sendMessageWithKey(key, message);
    }

    @PostMapping("/with-partition/{partition}")
    public void sendMessageWithKey(@PathVariable int partition, @RequestBody MessageEvent message) {
        kafkaMessageProducer.sendMessageWithPartition(partition, message);
    }
}
