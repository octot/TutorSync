package com.TutorSync.TutorSync.tuition.service.impl;

import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.repository.TuitionRecordRepository;
import com.TutorSync.TutorSync.tuition.service.TuitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class TuitionServiceImpl implements TuitionService {

    private final TuitionRecordRepository tuitionRecordRepository;

    @Override
    public TuitionRecord createTuition(CreateTuitionRequest request) {
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


        return tuitionRecordRepository.save(tuitionRecord);

    }

    @Override
    @Transactional(readOnly = true)
    public TuitionRecord getTuitionById(UUID id) {
        return tuitionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuition record not found"));

    }

    @Override
    public TuitionRecord updateTuition(UUID id, UpdateTuitionRequest request) {
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

        return tuitionRecordRepository.save(record);

    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }


    private String trimNullable(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TuitionRecord> findByTuitionId(String id) {
        return tuitionRecordRepository.findByTuitionId(id);

    }


}
