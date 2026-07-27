package com.TutorSync.TutorSync.tuition.activity.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TuitionFieldChange {

    private String field;

    private String oldValue;

    private String newValue;
}
