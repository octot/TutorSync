package com.TutorSync.TutorSync.tuition.controller;


import com.TutorSync.TutorSync.common.dto.PageResponse;
import com.TutorSync.TutorSync.tuition.activity.dto.TuitionActivityResponse;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import com.TutorSync.TutorSync.tuition.service.TuitionActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tuition-activities")
@RequiredArgsConstructor
public class TuitionActivityController {

    private final TuitionActivityService tuitionActivityService;

    @GetMapping
    public ResponseEntity<PageResponse<TuitionActivityResponse>>
    getRecentActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)
            TuitionActivityType activityType) {

        PageResponse<TuitionActivityResponse> response =
                tuitionActivityService.getRecentActivities(
                        page,
                        size,
                        activityType
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tuitionId}")
    public ResponseEntity<PageResponse<TuitionActivityResponse>>
    getActivitiesByTuitionId(
            @PathVariable String tuitionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<TuitionActivityResponse> response =
                tuitionActivityService.getActivitiesByTuitionId(
                        tuitionId,
                        page,
                        size
                );

        return ResponseEntity.ok(response);
    }

}
