package com.TutorSync.TutorSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TutorSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorSyncApplication.class, args);
	}

}
