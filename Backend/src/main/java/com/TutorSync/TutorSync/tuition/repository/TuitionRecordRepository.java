package com.TutorSync.TutorSync.tuition.repository;

import com.TutorSync.TutorSync.tuition.entity.TuitionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TuitionRecordRepository extends JpaRepository<TuitionRecord, UUID> {

    Optional<TuitionRecord> findByTuitionId(String tuitionId);

    boolean existsByTuitionId(String tuitionId);

}

