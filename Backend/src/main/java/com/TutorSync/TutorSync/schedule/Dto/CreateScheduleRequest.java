package com.TutorSync.TutorSync.schedule.Dto;

import com.TutorSync.TutorSync.message.dto.RecipientType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateScheduleRequest {

    private UUID tuitionId;
    private LocalDate demoDate;
    private List<RecipientType> recipients;

    // Getter and Setter for tuitionId
    public UUID getTuitionId() {
        return tuitionId;
    }

    public void setTuitionId(UUID tuitionId) {
        this.tuitionId = tuitionId;
    }

    // Getter and Setter for demoDate
    public LocalDate getDemoDate() {
        return demoDate;
    }

    public void setDemoDate(LocalDate demoDate) {
        this.demoDate = demoDate;
    }

    // Getter and Setter for recipients
    public List<RecipientType> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientType> recipients) {
        this.recipients = recipients;
    }
}
