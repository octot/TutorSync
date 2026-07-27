package com.TutorSync.TutorSync.message.service;

import com.TutorSync.TutorSync.message.dto.GeneratedMessage;
import com.TutorSync.TutorSync.message.dto.MessageRequest;
import com.TutorSync.TutorSync.message.dto.MessageResponse;
import com.TutorSync.TutorSync.message.dto.SendMessageRequest;
import com.TutorSync.TutorSync.message.generator.ParentMessageGenerator;
import com.TutorSync.TutorSync.message.generator.TutorMessageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final TutorMessageGenerator tutorMessageTemplate;
    private final ParentMessageGenerator parentMessageTemplate;
    private final WhatsappService whatsappService;

    public MessageResponse generateMessages(MessageRequest request) {
        List<GeneratedMessage> messages = generate(request);

        return buildResponse(messages);
    }

    private List<GeneratedMessage> generate(MessageRequest request) {

        return List.of(
                tutorMessageTemplate.generate(request),
                parentMessageTemplate.generate(request)
        );
    }

    private MessageResponse buildResponse(List<GeneratedMessage> messages) {

        MessageResponse response = new MessageResponse();
        response.setMessages(messages);

        return response;
    }

    public void sendToAdmin(SendMessageRequest request) {
        whatsappService.sendToAdmin(request.getMessages());
    }

}
