package com.TutorSync.TutorSync.tuition.activity.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TuitionChangeResult {

    private List<TuitionFieldChange> fieldChanges;

    private String remarks;
}
