package com.TutorSync.TutorSync.tuition.service;

import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.TuitionResponse;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;

import java.util.UUID;

public interface TuitionService {

    TuitionResponse createTuition(CreateTuitionRequest request);

    TuitionResponse getTuitionById(UUID id);

    TuitionResponse updateTuition(UUID id, UpdateTuitionRequest request);

    TuitionResponse findByTuitionId(String tuitionId);
}
