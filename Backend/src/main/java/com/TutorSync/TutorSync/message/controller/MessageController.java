package com.smarthr.smarthr.message.controller;

import com.smarthr.smarthr.message.dto.MessageRequest;
import com.smarthr.smarthr.message.dto.MessageResponse;
import com.smarthr.smarthr.message.dto.SendMessageRequest;
import com.smarthr.smarthr.message.dto.SendMessageResponse;
import com.smarthr.smarthr.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/generate")
    public ResponseEntity<MessageResponse> generateMessages(
            @RequestBody MessageRequest request
    ) {

        MessageResponse response = messageService.generateMessages(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public ResponseEntity<SendMessageResponse> sendToAdmin(
            @RequestBody SendMessageRequest request) {
        messageService.sendToAdmin(request);
        return ResponseEntity.ok(
                SendMessageResponse.builder()
                        .success(true)
                        .message("Messages sent successfully.")
                        .build()
        );
    }
}
