package com.TutorSync.TutorSync.message.service;

import com.TutorSync.TutorSync.message.dto.GeneratedMessage;

import java.util.List;

public interface WhatsappService {
    void sendToAdmin(List<GeneratedMessage> messages);
}
