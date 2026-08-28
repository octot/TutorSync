package com.TutorSync.TutorSync.schedule.controller;


import com.TutorSync.TutorSync.schedule.Dto.CreateScheduleRequest;
import com.TutorSync.TutorSync.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateScheduleRequest request) {
        scheduleService.createSchedule(request);
        return ResponseEntity.ok("Schedule created");
    }
}
