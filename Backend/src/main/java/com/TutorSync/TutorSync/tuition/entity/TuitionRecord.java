package com.TutorSync.TutorSync.tuition.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tuition_records",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tuition_records_tuition_id", columnNames = "tuition_id")
        }
)

@Getter
@Setter
public class TuitionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tuition_id", nullable = false, unique = true, length = 100)
    private String tuitionId;

    @Column(name = "tutor_name", nullable = false, length = 150)
    private String tutorName;

    @Column(name = "tutor_number", nullable = false, length = 20)
    private String tutorNumber;
    @Column(name = "parent_name", length = 150)
    private String parentName;

    @Column(name = "parent_number", nullable = false, length = 20)
    private String parentNumber;

    @Column(name = "start_time", nullable = false, length = 10)
    private String startTime;

    @Column(name = "end_time", nullable = false, length = 10)
    private String endTime;

    @Column(name = "time_zone", nullable = false, length = 100)
    private String timeZone;
    @Column(name = "payment_from_parent", length = 100)
    private String paymentFromParent;

    @Column(name = "payment_to_tutor", length = 100)
    private String paymentToTutor;

    @Column(name = "additional_message_to_tutor", columnDefinition = "TEXT")
    private String additionalMessageToTutor;

    @Column(name = "additional_message_to_parent", columnDefinition = "TEXT")
    private String additionalMessageToParent;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
