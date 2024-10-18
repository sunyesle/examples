package com.sunyesle.spring_boot_kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "my-topic")
    public void listen(String message){
        System.out.println("Received message: " + message);
    }
}
