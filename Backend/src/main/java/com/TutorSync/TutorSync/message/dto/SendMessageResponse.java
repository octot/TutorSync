package com.smarthr.smarthr.message.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SendMessageResponse {


    private boolean success;

    private String message;


}
