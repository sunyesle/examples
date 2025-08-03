package com.sunyesle.spring_boot_kafka.v2.service;

import com.sunyesle.spring_boot_kafka.avro.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Profile("kafka-schema-registry")
@Component
@RequiredArgsConstructor
public class KafkaAvroMessageProducer {
    private static final String TOPIC_NAME = "message-topic";

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    public void sendMessage(MessageEvent message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendMessageWithKey(String key, MessageEvent message) {
        kafkaTemplate.send(TOPIC_NAME, key, message);
    }

    public void sendMessageWithPartition(int partition, MessageEvent message) {
        kafkaTemplate.send(TOPIC_NAME, partition, "", message);
    }
}
