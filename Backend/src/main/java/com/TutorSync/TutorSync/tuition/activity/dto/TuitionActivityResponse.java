package com.TutorSync.TutorSync.tuition.activity.dto;


import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class TuitionActivityResponse {

    private UUID id;

    private String tuitionId;

    private TuitionActivityType activityType;

    private LocalDateTime performedAt;

    private String remarks;
    private List<TuitionFieldChange> fieldChanges;
}
