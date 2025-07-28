package com.sunyesle.spring_boot_kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer {
    private static final String TOPIC_NAME = "message-topic";

    @KafkaListener(topics = TOPIC_NAME)
    public void listen(ConsumerRecord<String, String> data) {
        log.info("Consumed message: key={}, value={}, partition={}, offset={}",
                data.key(), data.value(), data.partition(), data.offset());
    }
}
