package com.TutorSync.TutorSync.workflow;


import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionRequest;
import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tuition-submissions")
@RequiredArgsConstructor
public class TuitionSubmissionController {
    private final TuitionSubmissionService tuitionSubmissionService;

    @PostMapping
    public ResponseEntity<SubmitTuitionResponse> submit(
            @RequestBody SubmitTuitionRequest request) {

        SubmitTuitionResponse response =
                tuitionSubmissionService.submit(request);

        return ResponseEntity.ok(response);
    }


}
