package com.TutorSync.TutorSync.tuition.entity;

import com.TutorSync.TutorSync.tuition.activity.dto.TuitionFieldChange;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TuitionActivity {
    @PrePersist
    public void prePersist() {
        this.performedAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuition_record_id", nullable = false)
    private TuitionRecord tuition;

    @Column(name = "tuition_id", nullable = false)
    private String tuitionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TuitionActivityType activityType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime performedAt;

    @Column(length = 500)
    private String remarks;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "field_changes", columnDefinition = "jsonb")
    private List<TuitionFieldChange> fieldChanges;

}
