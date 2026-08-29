package com.TutorSync.TutorSync.schedule.service;


import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import com.TutorSync.TutorSync.schedule.repository.ScheduleExecutionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutionEngine {
    private final ScheduleExecutionRepository executionRepository;

    public ExecutionEngine(ScheduleExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    public void process(ScheduleExecution exec) {

        System.out.println("Processing execution: " + exec.getId());

        try {
            // 🔥 Day 2 → just simulate
            System.out.println("Sending message to: " + exec.getRecipientType());

            // mark success
            exec.setStatus(ExecutionStatus.SENT);
            executionRepository.save(exec);

            //TODO Need to add global exception
        } catch (Exception e) {
            System.out.println("Execution failed: " + exec.getId());
            exec.setStatus(ExecutionStatus.FAILED);
            executionRepository.save(exec);
        }
    }
}
