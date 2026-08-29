package com.TutorSync.TutorSync.schedule;

import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import com.TutorSync.TutorSync.schedule.repository.ScheduleExecutionRepository;
import com.TutorSync.TutorSync.schedule.service.ExecutionEngine;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduleWorker {


    private final ScheduleExecutionRepository executionRepository;
    private final ExecutionEngine executionEngine;

    public ScheduleWorker(ScheduleExecutionRepository executionRepository, ExecutionEngine executionEngine) {
        this.executionRepository = executionRepository;
        this.executionEngine = executionEngine;
    }

    @Scheduled(fixedDelayString = "${scheduler.worker.delay}")
    public void runWorker() {
        List<ScheduleExecution> executions =
                executionRepository.findByStatusAndScheduledTimeLessThanEqual(
                        ExecutionStatus.SCHEDULED,
                        LocalDateTime.now()
                );
        System.out.println("Found executions: " + executions.size());
        for (ScheduleExecution exec : executions) {

            int updated = executionRepository.lockExecution(exec.getId());

            if (updated == 1) {
                // ✅ SUCCESS → lock acquired
                System.out.println("LOCKED execution: " + exec.getId());

                //Delegate work
                executionEngine.process(exec);
            } else {
                System.out.println("SKIPPED (already locked): " + exec.getId());
            }
            System.out.println("Execution ID: " + exec.getId() +
                    " | Recipient: " + exec.getRecipientType());
        }
    }
}
