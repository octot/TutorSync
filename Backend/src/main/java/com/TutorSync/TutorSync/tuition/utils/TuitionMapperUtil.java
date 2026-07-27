package com.TutorSync.TutorSync.tuition.utils;

import com.TutorSync.TutorSync.message.dto.Schedule;
import com.TutorSync.TutorSync.tuition.dto.TuitionResponse;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;

public class TuitionMapperUtil {
    public static TuitionResponse mapToResponse(TuitionRecord record) {
        TuitionResponse response = new TuitionResponse();
        response.setId(record.getId());
        response.setTuitionId(record.getTuitionId());
        response.setTutorName(record.getTutorName());
        response.setTutorNumber(record.getTutorNumber());
        response.setParentName(record.getParentName());
        response.setParentNumber(record.getParentNumber());
        response.setPaymentFromParent(record.getPaymentFromParent());
        response.setPaymentToTutor(record.getPaymentToTutor());
        response.setAdditionalMessageToTutor(record.getAdditionalMessageToTutor());
        response.setAdditionalMessageToParent(record.getAdditionalMessageToParent());
        response.setCreatedAt(record.getCreatedAt());
        response.setUpdatedAt(record.getUpdatedAt());

        Schedule schedule = new Schedule();
        schedule.setStartTime(record.getStartTime());
        schedule.setEndTime(record.getEndTime());
        schedule.setTimeZone(record.getTimeZone());
        response.setSchedule(schedule);
        return response;
    }
}
