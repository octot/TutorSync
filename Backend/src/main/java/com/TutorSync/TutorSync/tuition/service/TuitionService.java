package com.TutorSync.TutorSync.tuition.service;

import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;

import java.util.Optional;
import java.util.UUID;

public interface TuitionService {

    TuitionRecord  createTuition(CreateTuitionRequest request);

    TuitionRecord  getTuitionById(UUID id);

    TuitionRecord  updateTuition(UUID id, UpdateTuitionRequest request);

    Optional<TuitionRecord> findByTuitionId(String tuitionId);


}
