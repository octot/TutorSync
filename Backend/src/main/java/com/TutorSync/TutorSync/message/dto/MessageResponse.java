package com.smarthr.smarthr.message.dto;


import lombok.Data;

import java.util.List;

@Data
public class MessageResponse {

    private List<GeneratedMessage> messages;
}
