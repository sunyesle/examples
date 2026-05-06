package com.sunyesle.spring_boot_kafka.v1.service;

import com.sunyesle.spring_boot_kafka.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@Profile({"kafka-manual-commit"})
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaAcknowledgingMessageListener {
    private static final String TOPIC_NAME = "message-topic";

    @KafkaListener(topics = TOPIC_NAME)
    public void listen(ConsumerRecord<String, MessageDto> data, Acknowledgment ack) {
        log.info("Consumed message: key={}, value={}, partition={}, offset={}",
                data.key(), data.value(), data.partition(), data.offset());
        ack.acknowledge(); // offset 수동 커밋
    }
}
