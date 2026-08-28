package com.TutorSync.TutorSync.schedule.entity;

import com.TutorSync.TutorSync.message.dto.RecipientType;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedule_execution")
public class ScheduleExecution {

    @Id
    private UUID id;
    private UUID scheduleId;

    @Enumerated(EnumType.STRING)
    private RecipientType recipientType;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    private int retryCount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- Constructors ---
    public ScheduleExecution() {
        // Default constructor required by JPA
    }

    public ScheduleExecution(UUID id, UUID scheduleId, RecipientType recipientType,
                             ExecutionStatus status, int retryCount,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.recipientType = recipientType;
        this.status = status;
        this.retryCount = retryCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // --- Getters and Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
