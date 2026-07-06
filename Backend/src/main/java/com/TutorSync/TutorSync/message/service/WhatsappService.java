package com.smarthr.smarthr.message.service;

import com.smarthr.smarthr.message.dto.GeneratedMessage;

import java.util.List;

public interface WhatsappService {
    void sendToAdmin(List<GeneratedMessage> messages);
}
