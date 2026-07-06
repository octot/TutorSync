package com.TutorSync.TutorSync.message.generator;

import com.TutorSync.TutorSync.message.dto.GeneratedMessage;
import com.TutorSync.TutorSync.message.dto.MessageRequest;

public interface MessageTemplate {
    GeneratedMessage generate(MessageRequest request);
}
