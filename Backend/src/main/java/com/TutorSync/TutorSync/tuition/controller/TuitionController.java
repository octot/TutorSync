package com.TutorSync.TutorSync.tuition.controller;

import com.TutorSync.TutorSync.tuition.dto.CreateTuitionRequest;
import com.TutorSync.TutorSync.tuition.dto.TuitionResponse;
import com.TutorSync.TutorSync.tuition.dto.UpdateTuitionRequest;
import com.TutorSync.TutorSync.tuition.service.TuitionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tuitions")
public class TuitionController {

    private final TuitionService tuitionService;

    public TuitionController(TuitionService tuitionService) {
        this.tuitionService = tuitionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TuitionResponse createTuition(@Valid @RequestBody CreateTuitionRequest request) {
        return tuitionService.createTuition(request);
    }

    //One for db and other for frontend
    @GetMapping("/{id}")
    public TuitionResponse getTuitionById(@PathVariable UUID id) {
        return tuitionService.getTuitionById(id);
    }

    @GetMapping("/by-tuition-id/{tuitionId}")
    public TuitionResponse getTuitionByTuitionId(@PathVariable String tuitionId) {
        return tuitionService.findByTuitionId(tuitionId);
    }
    @PutMapping("/{id}")
    public TuitionResponse updateTuition(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTuitionRequest request
    ) {
        return tuitionService.updateTuition(id, request);
    }
}
