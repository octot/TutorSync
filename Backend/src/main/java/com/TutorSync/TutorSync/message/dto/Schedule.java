package com.TutorSync.TutorSync.message.dto;


import lombok.Data;

@Data
public class Schedule {

    private String startTime;

    private String endTime;

    private String timeZone;
}
