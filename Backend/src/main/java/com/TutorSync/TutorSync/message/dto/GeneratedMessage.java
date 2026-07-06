package com.smarthr.smarthr.message.dto;


import lombok.Data;

@Data
public class GeneratedMessage {

    private RecipientType recipientType;

    private String recipientName;

    private String recipientNumber;

    private String message;
}
