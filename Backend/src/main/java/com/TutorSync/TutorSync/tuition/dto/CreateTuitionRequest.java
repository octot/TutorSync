package com.TutorSync.TutorSync.tuition.dto;


import com.TutorSync.TutorSync.message.dto.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTuitionRequest extends TuitionRequest {
    @NotBlank(message = "Tuition ID is required")
    private String tuitionId;


}
