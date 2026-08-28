package com.TutorSync.TutorSync.schedule.repository;


import com.TutorSync.TutorSync.schedule.entity.ScheduleExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleExecutionRepository extends JpaRepository<ScheduleExecution, UUID> {}