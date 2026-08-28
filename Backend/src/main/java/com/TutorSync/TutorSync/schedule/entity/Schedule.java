package com.TutorSync.TutorSync.schedule.entity;

import com.TutorSync.TutorSync.schedule.enums.ScheduleStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    private UUID id;

    private UUID tuitionId;

    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // --- Constructors ---
    public Schedule() {
        // Default constructor required by JPA
    }

    public Schedule(UUID id, UUID tuitionId, LocalDateTime scheduledTime,
                    ScheduleStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.tuitionId = tuitionId;
        this.scheduledTime = scheduledTime;
        this.status = status;
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

    public UUID getTuitionId() {
        return tuitionId;
    }

    public void setTuitionId(UUID tuitionId) {
        this.tuitionId = tuitionId;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
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
