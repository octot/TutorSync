package com.TutorSync.TutorSync.tuition.utils;


import com.TutorSync.TutorSync.tuition.activity.dto.TuitionChangeResult;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionFieldChange;
import com.TutorSync.TutorSync.tuition.dto.TuitionRequest;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TuitionChangeTracker {
    private static final List<FieldMapping> TRACKED_FIELDS = List.of(
            field(
                    "Tutor Name",
                    TuitionRecord::getTutorName,
                    TuitionRequest::getTutorName
            ),

            field(
                    "Tutor Number",
                    TuitionRecord::getTutorNumber,
                    TuitionRequest::getTutorNumber
            ),

            field(
                    "Parent Name",
                    TuitionRecord::getParentName,
                    TuitionRequest::getParentName
            ),

            field(
                    "Parent Number",
                    TuitionRecord::getParentNumber,
                    TuitionRequest::getParentNumber
            ),

            field(
                    "Payment From Parent",
                    TuitionRecord::getPaymentFromParent,
                    TuitionRequest::getPaymentFromParent
            ),

            field(
                    "Payment To Tutor",
                    TuitionRecord::getPaymentToTutor,
                    TuitionRequest::getPaymentToTutor
            ),

            field(
                    "Additional Message To Tutor",
                    TuitionRecord::getAdditionalMessageToTutor,
                    TuitionRequest::getAdditionalMessageToTutor
            ),

            field(
                    "Additional Message To Parent",
                    TuitionRecord::getAdditionalMessageToParent,
                    TuitionRequest::getAdditionalMessageToParent
            ),

            field(
                    "Start Time",
                    TuitionRecord::getStartTime,
                    request -> request.getSchedule() != null
                            ? request.getSchedule().getStartTime()
                            : null
            ),

            field(
                    "End Time",
                    TuitionRecord::getEndTime,
                    request -> request.getSchedule() != null
                            ? request.getSchedule().getEndTime()
                            : null
            ),

            field(
                    "Time Zone",
                    TuitionRecord::getTimeZone,
                    request -> request.getSchedule() != null
                            ? request.getSchedule().getTimeZone()
                            : null
            )
    );


    public TuitionChangeResult trackChanges(
            TuitionRecord existingRecord,
            TuitionRequest incomingRequest) {

        List<TuitionFieldChange> changes = new ArrayList<>();
        for (FieldMapping field : TRACKED_FIELDS) {

            TuitionFieldChange change = buildChange(
                    field,
                    existingRecord,
                    incomingRequest
            );

            if (change != null) {
                changes.add(change);
            }
        }
        String remarks;
        if (changes.isEmpty()) {
            remarks = "No tuition details changed.";
        } else {
            remarks = changes.stream()
                    .map(change -> "%s changed from '%s' to '%s'"
                            .formatted(
                                    change.getField(),
                                    change.getOldValue(),
                                    change.getNewValue()
                            ))
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        return TuitionChangeResult.builder().fieldChanges(changes).remarks(remarks).build();

    }

    private TuitionFieldChange buildChange(
            FieldMapping field,
            TuitionRecord existingRecord,
            TuitionRequest incomingRequest) {

        Object oldValue =
                field.oldValueExtractor().apply(existingRecord);

        Object newValue =
                field.newValueExtractor().apply(incomingRequest);

        if (Objects.equals(oldValue, newValue)) {
            return null;
        }
        return TuitionFieldChange.builder()
                .field(field.displayName())
                .oldValue(formatValue(oldValue))
                .newValue(formatValue(newValue))
                .build();
    }


    private String formatValue(Object value) {
        return value == null
                ? "N/A"
                : value.toString();
    }


    private static FieldMapping field(
            String displayName,
            Function<TuitionRecord, Object> oldValueExtractor,
            Function<TuitionRequest, Object> newValueExtractor) {

        return new FieldMapping(
                displayName,
                oldValueExtractor,
                newValueExtractor
        );
    }

    private record FieldMapping(
            String displayName,
            Function<TuitionRecord, Object> oldValueExtractor,
            Function<TuitionRequest, Object> newValueExtractor
    ) {
    }
}

