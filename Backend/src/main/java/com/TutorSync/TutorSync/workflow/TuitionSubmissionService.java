package com.TutorSync.TutorSync.workflow;

import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionRequest;
import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionResponse;

public interface TuitionSubmissionService {

    SubmitTuitionResponse submit(SubmitTuitionRequest request);

}
