package com.sunyesle.spring_boot_kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "my-topic", groupId = "notification")
    public void listen(String message) {
        System.out.println("Received notification message: " + message);
    }
}
