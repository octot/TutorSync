package com.TutorSync.TutorSync.tuition.service;

import com.TutorSync.TutorSync.common.dto.PageResponse;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionActivityResponse;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionChangeResult;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import jakarta.annotation.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface TuitionActivityService {

    void record(
            TuitionRecord tuition,
            TuitionActivityType activityType,
            @Nullable TuitionChangeResult changeResult
    );

    void record(
            TuitionRecord tuition,
            TuitionActivityType activityType,
            String remarks
    );

    PageResponse<TuitionActivityResponse> getRecentActivities(
            int page,
            int size,
            TuitionActivityType activityType
    );


    PageResponse<TuitionActivityResponse> getActivitiesByTuitionId(
            String tuitionId,
            int page,
            int size);
}
