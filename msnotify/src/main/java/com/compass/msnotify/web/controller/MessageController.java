package com.compass.msnotify.web.controller;

import com.compass.msnotify.document.Message;
import com.compass.msnotify.repository.MessageRepository;
import com.compass.msnotify.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    ResponseEntity<List<Message>> findAll() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
