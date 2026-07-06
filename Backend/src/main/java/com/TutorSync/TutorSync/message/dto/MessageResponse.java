package com.TutorSync.TutorSync.message.dto;


import lombok.Data;

import java.util.List;

@Data
public class MessageResponse {

    private List<GeneratedMessage> messages;
}
