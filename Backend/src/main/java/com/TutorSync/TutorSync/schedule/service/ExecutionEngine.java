package com.TutorSync.TutorSync.schedule.service;


import com.TutorSync.TutorSync.message.dto.GeneratedMessage;
import com.TutorSync.TutorSync.message.dto.MessageRequest;
import com.TutorSync.TutorSync.message.dto.RecipientType;
import com.TutorSync.TutorSync.message.service.MessageService;
import com.TutorSync.TutorSync.message.service.WhatsappService;
import com.TutorSync.TutorSync.schedule.entity.Schedule;
import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import com.TutorSync.TutorSync.schedule.repository.ScheduleExecutionRepository;
import com.TutorSync.TutorSync.schedule.repository.ScheduleRepository;
import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import com.TutorSync.TutorSync.tuition.repository.TuitionRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExecutionEngine {
    private final ScheduleExecutionRepository executionRepository;
    private final MessageService messageService;
    private final WhatsappService whatsappService;
    private final ScheduleRepository scheduleRepository;
    private final TuitionRecordRepository tuitionRecordRepository;

    public ExecutionEngine(ScheduleExecutionRepository executionRepository, MessageService messageService, WhatsappService whatsappService, ScheduleRepository scheduleRepository, TuitionRecordRepository tuitionRecordRepository) {
        this.executionRepository = executionRepository;
        this.messageService = messageService;
        this.whatsappService = whatsappService;
        this.scheduleRepository = scheduleRepository;
        this.tuitionRecordRepository = tuitionRecordRepository;
    }

    public void process(ScheduleExecution exec) {

        System.out.println("Processing execution: " + exec.getId());

        try {
            // 1. Fetch Schedule
            Schedule schedule = scheduleRepository.findById(exec.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));

            // 2. Fetch Tuition
            TuitionRecord tuition = tuitionRecordRepository.findById(schedule.getTuitionId())
                    .orElseThrow(() -> new RuntimeException("Tuition not found"));

            MessageRequest request = buildMessageRequest(schedule, tuition);

            // 🔥 4. Generate message
            GeneratedMessage message =
                    messageService.generateForRecipient(request, exec.getRecipientType());

//            String phone = getPhoneNumber(exec, tuition);
//            whatsappService.send(phone, message.getContent());
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

    private MessageRequest buildMessageRequest(Schedule schedule, TuitionRecord tuition) {

        // 3. Build request
        MessageRequest request = new MessageRequest();
        request.setParentName(tuition.getParentName());
        request.setTutorName(tuition.getTutorName());

        request.setDemoDateTime(schedule.getScheduledTime());

        return request;
    }

    private String getPhoneNumber(ScheduleExecution exec, TuitionRecord tuition) {

        if (exec.getRecipientType() == RecipientType.TUTOR) {
            return tuition.getTutorNumber();
        } else {
            return tuition.getParentNumber();
        }
    }

}
