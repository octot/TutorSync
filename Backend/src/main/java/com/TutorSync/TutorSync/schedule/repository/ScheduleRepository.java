package com.TutorSync.TutorSync.schedule.repository;

import com.TutorSync.TutorSync.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {}