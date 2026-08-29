package com.TutorSync.TutorSync.schedule.service;


import com.TutorSync.TutorSync.message.dto.RecipientType;
import com.TutorSync.TutorSync.schedule.Dto.CreateScheduleRequest;
import com.TutorSync.TutorSync.schedule.entity.Schedule;
import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import com.TutorSync.TutorSync.schedule.enums.ScheduleStatus;
import com.TutorSync.TutorSync.schedule.repository.ScheduleExecutionRepository;
import com.TutorSync.TutorSync.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleExecutionRepository executionRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           ScheduleExecutionRepository executionRepository) {
        this.scheduleRepository = scheduleRepository;
        this.executionRepository = executionRepository;
    }

    public void createSchedule(CreateScheduleRequest request) {
        // 1. Compute scheduled time (7 AM fixed)
        LocalDateTime scheduledTime = request.getDemoDate().atTime(7, 0);

        // 2. Create Schedule
        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setTuitionId(request.getTuitionId());
        schedule.setScheduledTime(scheduledTime);
        schedule.setStatus(ScheduleStatus.SCHEDULED);
        schedule.setCreatedAt(LocalDateTime.now());

        scheduleRepository.save(schedule);

        // 3. Create executions (Tutor / Parent)
        List<ScheduleExecution> executions = new ArrayList<>();

        for (RecipientType recipient : request.getRecipients()) {
            ScheduleExecution execution = new ScheduleExecution();
            execution.setId(UUID.randomUUID());
            execution.setScheduleId(schedule.getId());
            execution.setRecipientType(recipient);
            execution.setStatus(ExecutionStatus.SCHEDULED);
            execution.setRetryCount(0);
            execution.setCreatedAt(LocalDateTime.now());
            execution.setScheduledTime(scheduledTime);

            executions.add(execution);
        }
        executionRepository.saveAll(executions);

    }
}
