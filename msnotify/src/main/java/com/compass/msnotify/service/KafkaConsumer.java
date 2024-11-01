package com.compass.msnotify.service;

import com.compass.msnotify.document.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final MessageService messageService;

    @KafkaListener(topics = "msusers_topic", groupId = "msnotify-group")
    public void consume(String message) {
        Message m1 = new Message(message, LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        messageService.saveMessage(m1);
        log.info("Message: {} ", message);
    }
}
