package com.mx.development.said.olano.controller;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.helpers.JobsHelper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/job")
public class JobLauncherController {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired private ApplicationContext applicationContext;
	@Autowired private JobsHelper jobsHelper;

	@GetMapping("/launcher")
	public Object launcher() throws Exception {
		System.out.println("API Call done!");
		//Call first job
		jobsHelper.executeJob(jobLauncher, applicationContext, Constants.CLEAN_UP_WORKING_DIRECTORY_OS_JOB);
		//Call second job
		jobsHelper.executeJob(jobLauncher, applicationContext, Constants.RETRIEVE_NEWS_JOB);
		//Call third job
		jobsHelper.executeJob(jobLauncher, applicationContext, Constants.NEWS_PROCESS_JOB);

		return "JobLauncherController was executed successfully at: " + LocalDateTime.now();
	}

}
