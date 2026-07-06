package com.TutorSync.TutorSync.message.controller;

import com.TutorSync.TutorSync.message.dto.MessageRequest;
import com.TutorSync.TutorSync.message.dto.MessageResponse;
import com.TutorSync.TutorSync.message.dto.SendMessageRequest;
import com.TutorSync.TutorSync.message.dto.SendMessageResponse;
import com.TutorSync.TutorSync.message.service.MessageService;
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
