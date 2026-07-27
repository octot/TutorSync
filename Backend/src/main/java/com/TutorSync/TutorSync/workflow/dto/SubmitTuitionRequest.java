package com.TutorSync.TutorSync.workflow.dto;


import com.TutorSync.TutorSync.message.dto.SendMessageRequest;
import com.TutorSync.TutorSync.tuition.dto.TuitionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTuitionRequest {

    private String tuitionId;
    private TuitionRequest tuitionRequest;
    private SendMessageRequest messageRequest;




}
