package com.TutorSync.TutorSync.workflow.impl;

import com.TutorSync.TutorSync.message.service.MessageService;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionChangeResult;
import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import com.TutorSync.TutorSync.tuition.service.TuitionActivityService;
import com.TutorSync.TutorSync.tuition.service.TuitionService;
import com.TutorSync.TutorSync.tuition.utils.ActivityRemarks;
import com.TutorSync.TutorSync.tuition.utils.TuitionChangeTracker;
import com.TutorSync.TutorSync.workflow.TuitionSubmissionService;
import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionRequest;
import com.TutorSync.TutorSync.workflow.dto.SubmitTuitionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TuitionSubmissionServiceImpl implements TuitionSubmissionService {

    private final TuitionService tuitionService;
    private final MessageService messageService;
    private final TuitionActivityService tuitionActivityService;
    private final TuitionChangeTracker tuitionChangeTracker;

    /*
Search + select existing ID  ──┐
                               ├── ID exists ──► UPDATE
Manually type existing ID ─────┘

Manually type new ID ────────────── ID absent ──► CREATE
 */
    @Override
    public SubmitTuitionResponse submit(SubmitTuitionRequest request) {

        Optional<TuitionRecord> tuitionRecord =
                tuitionService.findByTuitionId(request.getTuitionId());

        // User searched and loaded an existing tuition
        if (tuitionRecord.isPresent()) {
            return handleUpdate(request, tuitionRecord.get());
        }

        // tuitionId does not exist
        return handleCreate(request);
    }


    private SubmitTuitionResponse handleUpdate(
            SubmitTuitionRequest request,
            TuitionRecord tuitionRecord) {
        UpdateTuitionRequest updateRequest =
                new UpdateTuitionRequest();

        BeanUtils.copyProperties(
                request.getTuitionRequest(),
                updateRequest
        );

        TuitionChangeResult changes =
                tuitionChangeTracker.trackChanges(
                        tuitionRecord,
                        request.getTuitionRequest()
                );

        TuitionRecord updatedRecord = tuitionService.updateTuition(
                tuitionRecord.getId(),
                updateRequest);

        tuitionActivityService.record(
                updatedRecord,
                TuitionActivityType.UPDATED,
                changes
        );
        sendMessages(request, updatedRecord);

        return buildSuccessResponse(updatedRecord);
    }

    private SubmitTuitionResponse handleCreate(SubmitTuitionRequest request) {

        CreateTuitionRequest createRequest =
                new CreateTuitionRequest();

        BeanUtils.copyProperties(
                request.getTuitionRequest(),
                createRequest
        );
        createRequest.setTuitionId(request.getTuitionId());

        TuitionRecord tuitionRecord =
                tuitionService.createTuition(createRequest);


        //Logging Tuition creation
        tuitionActivityService.record(
                tuitionRecord,
                TuitionActivityType.CREATED,
                ActivityRemarks.CREATED
        );

        sendMessages(request, tuitionRecord);

        return buildSuccessResponse(tuitionRecord);
    }


    private SubmitTuitionResponse buildSuccessResponse(
            TuitionRecord tuitionRecord) {

        return SubmitTuitionResponse.builder()
                .success(true)
                .message("Tuition submitted successfully.")
                .tuitionId(tuitionRecord.getTuitionId())
                .build();
    }

    private void sendMessages(
            SubmitTuitionRequest request,
            TuitionRecord tuitionRecord) {

        try {

            messageService.sendToAdmin(
                    request.getMessageRequest()
            );

            tuitionActivityService.record(
                    tuitionRecord,
                    TuitionActivityType.MESSAGE_SENT,

                    "Messages sent successfully."
            );

        } catch (Exception exception) {
            tuitionActivityService.record(
                    tuitionRecord,
                    TuitionActivityType.MESSAGE_SEND_FAILED,
                    "Failed to send messages: " + exception.getMessage()
            );

            throw exception;
        }
    }
}
