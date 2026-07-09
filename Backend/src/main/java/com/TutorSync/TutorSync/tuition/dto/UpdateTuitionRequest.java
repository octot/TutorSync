package com.TutorSync.TutorSync.tuition.dto;

import com.TutorSync.TutorSync.message.dto.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTuitionRequest {
    @NotBlank(message = "Tutor name is required")
    private String tutorName;

    @NotBlank(message = "Tutor number is required")
    private String tutorNumber;

    private String parentName;

    @NotBlank(message = "Parent number is required")
    private String parentNumber;

    private Schedule schedule;

    private String paymentFromParent;
    private String paymentToTutor;
    private String additionalMessageToTutor;
    private String additionalMessageToParent;

}
