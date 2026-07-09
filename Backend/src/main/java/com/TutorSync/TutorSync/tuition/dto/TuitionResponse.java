package com.TutorSync.TutorSync.tuition.dto;

import com.TutorSync.TutorSync.message.dto.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TuitionResponse {
    private UUID id;
    private String tuitionId;
    private String tutorName;
    private String tutorNumber;
    private String parentName;
    private String parentNumber;
    private Schedule schedule;
    private String paymentFromParent;
    private String paymentToTutor;
    private String additionalMessageToTutor;
    private String additionalMessageToParent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
