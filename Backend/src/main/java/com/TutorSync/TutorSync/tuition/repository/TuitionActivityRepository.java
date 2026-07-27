package com.TutorSync.TutorSync.tuition.repository;


import com.TutorSync.TutorSync.tuition.entity.TuitionActivity;
import com.TutorSync.TutorSync.tuition.enums.TuitionActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TuitionActivityRepository extends JpaRepository<TuitionActivity, UUID> {

    Page<TuitionActivity> findAllByOrderByPerformedAtDesc(
            Pageable pageable
    );

    Page<TuitionActivity> findByActivityTypeOrderByPerformedAtDesc(
            TuitionActivityType activityType,
            Pageable pageable
    );

    Page<TuitionActivity> findByTuitionIdOrderByPerformedAtDesc(
            String tuitionId,
            Pageable pageable
    );


}
