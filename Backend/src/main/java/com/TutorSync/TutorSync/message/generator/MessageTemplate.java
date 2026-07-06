package com.smarthr.smarthr.message.generator;

import com.smarthr.smarthr.message.dto.GeneratedMessage;
import com.smarthr.smarthr.message.dto.MessageRequest;

public interface MessageTemplate {
    GeneratedMessage generate(MessageRequest request);
}
