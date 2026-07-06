package com.TutorSync.TutorSync.message.dto;


import lombok.Data;

@Data
public class MessageRequest {

    private String tuitionId;

    private String tutorName;
    private String tutorNumber;

    private String parentName;  //Or Use dear parent
    private String parentNumber;

    private Schedule schedule;

    private Double paymentFromParent;
    private Double paymentToTutor;

    private String additionalMessageToTutor;
    private String additionalMessageToParent;

}