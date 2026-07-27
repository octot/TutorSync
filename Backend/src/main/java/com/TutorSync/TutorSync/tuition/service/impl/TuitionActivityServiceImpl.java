package com.TutorSync.TutorSync.tuition.service.impl;


import com.TutorSync.TutorSync.common.dto.PageResponse;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionActivityResponse;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionChangeResult;
import com.TutorSync.TutorSync.tuition.entity.TuitionActivity;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import com.TutorSync.TutorSync.tuition.repository.TuitionActivityRepository;
import com.TutorSync.TutorSync.tuition.service.TuitionActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TuitionActivityServiceImpl implements TuitionActivityService {

    private final TuitionActivityRepository tuitionActivityRepository;

    @Override
    public void record(TuitionRecord tuition, TuitionActivityType activityType, TuitionChangeResult changeResult) {

        TuitionActivity.TuitionActivityBuilder builder = TuitionActivity.builder()
                .tuition(tuition)
                .tuitionId(tuition.getTuitionId())
                .activityType(activityType);

        if (changeResult != null) {
            builder
                    .remarks(changeResult.getRemarks())
                    .fieldChanges(changeResult.getFieldChanges());
        }
        tuitionActivityRepository.save(builder.build());
    }

    @Override
    public void record(TuitionRecord tuition, TuitionActivityType activityType, String remarks) {

        TuitionActivity activity = TuitionActivity.builder()
                .tuition(tuition)
                .tuitionId(tuition.getTuitionId())
                .activityType(activityType)
                .remarks(remarks)
                .build();

        tuitionActivityRepository.save(activity);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<TuitionActivityResponse> getRecentActivities(
            int page,
            int size,
            TuitionActivityType activityType) {

        Pageable pageable = PageRequest.of(page, size);

        Page<TuitionActivity> activityPage;

        if (activityType == null) {
            activityPage =
                    tuitionActivityRepository
                            .findAllByOrderByPerformedAtDesc(pageable);
        } else {
            activityPage =
                    tuitionActivityRepository
                            .findByActivityTypeOrderByPerformedAtDesc(
                                    activityType,
                                    pageable
                            );
        }

        List<TuitionActivityResponse> activities =
                activityPage.getContent()
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return PageResponse.<TuitionActivityResponse>builder()
                .content(activities)
                .page(activityPage.getNumber())
                .size(activityPage.getSize())
                .totalElements(activityPage.getTotalElements())
                .totalPages(activityPage.getTotalPages())
                .last(activityPage.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<TuitionActivityResponse> getActivitiesByTuitionId(
            String tuitionId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<TuitionActivity> activityPage =
                tuitionActivityRepository
                        .findByTuitionIdOrderByPerformedAtDesc(
                                tuitionId,
                                pageable
                        );

        List<TuitionActivityResponse> activities =
                activityPage.getContent()
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return PageResponse.<TuitionActivityResponse>builder()
                .content(activities)
                .page(activityPage.getNumber())
                .size(activityPage.getSize())
                .totalElements(activityPage.getTotalElements())
                .totalPages(activityPage.getTotalPages())
                .last(activityPage.isLast())
                .build();
    }

    private TuitionActivityResponse toResponse(
            TuitionActivity activity) {

        return TuitionActivityResponse.builder()
                .id(activity.getId())
                .tuitionId(activity.getTuitionId())
                .activityType(activity.getActivityType())
                .performedAt(activity.getPerformedAt())
                .remarks(activity.getRemarks())
                //This is to send empty list instead of null value in field changes
                .fieldChanges(
                        Optional.ofNullable(activity.getFieldChanges())
                                .orElse(List.of())
                )
                .build();
    }
}

