package com.compass.msnotify.web.controller;

import com.compass.msnotify.document.Message;
import com.compass.msnotify.repository.MessageRepository;
import com.compass.msnotify.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
@Tag(name = "Operation User's Messages Endpoint")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Get All Messages", description = "Get All Messages",
            tags = {"Message"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Message.class))
                                    )
                            }),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    @GetMapping
    ResponseEntity<List<Message>> findAll() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
