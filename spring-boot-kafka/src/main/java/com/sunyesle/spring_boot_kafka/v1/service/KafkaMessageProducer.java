package com.sunyesle.spring_boot_kafka.v1.service;

import com.sunyesle.spring_boot_kafka.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Profile({"kafka-single-broker", "kafka-multi-broker"})
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {
    private static final String TOPIC_NAME = "message-topic";

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public void sendMessage(MessageDto message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendMessageWithKey(String key, MessageDto message) {
        kafkaTemplate.send(TOPIC_NAME, key, message);
    }

    public void sendMessageWithPartition(int partition, MessageDto message) {
        kafkaTemplate.send(TOPIC_NAME, partition, "", message);
    }
}
