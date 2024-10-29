package com.compass.msnotify.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "msusers_topic", groupId = "msnotify-group")
    public void consume(String message) {
        System.out.println("Message: " + message);
    }
}
