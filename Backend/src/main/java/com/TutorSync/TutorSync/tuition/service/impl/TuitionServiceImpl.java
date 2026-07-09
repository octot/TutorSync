package com.TutorSync.TutorSync.tuition.service.impl;

import com.TutorSync.TutorSync.message.dto.Schedule;
import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.TuitionResponse;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.repository.TuitionRecordRepository;
import com.TutorSync.TutorSync.tuition.service.TuitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
public class TuitionServiceImpl implements TuitionService {

    private final TuitionRecordRepository tuitionRecordRepository;

    public TuitionServiceImpl(TuitionRecordRepository tuitionRecordRepository) {
        this.tuitionRecordRepository = tuitionRecordRepository;
    }

    @Override
    public TuitionResponse createTuition(CreateTuitionRequest request) {
        String normalizedTuitionId = trim(request.getTuitionId());

        if (tuitionRecordRepository.existsByTuitionId(normalizedTuitionId)) {
            throw new IllegalArgumentException("Tuition ID already exists");
        }

        TuitionRecord tuitionRecord = new TuitionRecord();
        tuitionRecord.setTuitionId(normalizedTuitionId);
        tuitionRecord.setTutorName(trim(request.getTutorName()));
        tuitionRecord.setTutorNumber(trim(request.getTutorNumber()));
        tuitionRecord.setParentName(trimNullable(request.getParentName()));
        tuitionRecord.setParentNumber(trim(request.getParentNumber()));
        tuitionRecord.setStartTime(trim(request.getSchedule().getStartTime()));
        tuitionRecord.setEndTime(trim(request.getSchedule().getEndTime()));
        tuitionRecord.setTimeZone(trim(request.getSchedule().getTimeZone()));
        tuitionRecord.setPaymentFromParent(trimNullable(request.getPaymentFromParent()));
        tuitionRecord.setPaymentToTutor(trimNullable(request.getPaymentToTutor()));
        tuitionRecord.setAdditionalMessageToTutor(trimNullable(request.getAdditionalMessageToTutor()));
        tuitionRecord.setAdditionalMessageToParent(trimNullable(request.getAdditionalMessageToParent()));

        TuitionRecord saved = tuitionRecordRepository.save(tuitionRecord);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TuitionResponse getTuitionById(UUID id) {
        TuitionRecord record = tuitionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuition record not found"));
        return mapToResponse(record);
    }

    @Override
    public TuitionResponse updateTuition(UUID id, UpdateTuitionRequest request) {
        TuitionRecord record = tuitionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuition record not found"));

        record.setTutorName(trim(request.getTutorName()));
        record.setTutorNumber(trim(request.getTutorNumber()));
        record.setParentName(trimNullable(request.getParentName()));
        record.setParentNumber(trim(request.getParentNumber()));
        record.setStartTime(trim(request.getSchedule().getStartTime()));
        record.setEndTime(trim(request.getSchedule().getEndTime()));
        record.setTimeZone(trim(request.getSchedule().getTimeZone()));
        record.setPaymentFromParent(trimNullable(request.getPaymentFromParent()));
        record.setPaymentToTutor(trimNullable(request.getPaymentToTutor()));
        record.setAdditionalMessageToTutor(trimNullable(request.getAdditionalMessageToTutor()));
        record.setAdditionalMessageToParent(trimNullable(request.getAdditionalMessageToParent()));

        TuitionRecord updated = tuitionRecordRepository.save(record);
        return mapToResponse(updated);
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }


    private String trimNullable(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private TuitionResponse mapToResponse(TuitionRecord record) {
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

    @Override
    @Transactional(readOnly = true)
    public TuitionResponse findByTuitionId(String id) {
        TuitionRecord record = tuitionRecordRepository.findByTuitionId(id)
                .orElseThrow(() -> new RuntimeException("Tuition record not found"));
        return mapToResponse(record);
    }
}
