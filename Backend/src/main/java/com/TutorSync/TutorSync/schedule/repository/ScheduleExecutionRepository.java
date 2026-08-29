package com.TutorSync.TutorSync.schedule.repository;


import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import com.TutorSync.TutorSync.schedule.enums.ExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleExecutionRepository extends JpaRepository<ScheduleExecution, UUID> {


    List<ScheduleExecution> findByStatusAndScheduledTimeLessThanEqual(
            ExecutionStatus status,
            LocalDateTime time
    );


    /*returns  number of rows affected (if update is success expecting one
    row (1) is returned or else 0 for edge cases more than 1 is returned*/
    @Transactional
    @Modifying
    @Query("""
                UPDATE ScheduleExecution se
                SET se.status = 'PROCESSING'
                WHERE se.id = :id
                AND se.status = 'SCHEDULED'
            """)
    int lockExecution(@Param("id") UUID id);


}