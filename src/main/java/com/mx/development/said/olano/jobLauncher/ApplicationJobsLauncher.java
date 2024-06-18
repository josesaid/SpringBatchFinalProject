package com.mx.development.said.olano.jobLauncher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.helpers.JobsHelper;

@Configuration
@Slf4j
public class ApplicationJobsLauncher implements CommandLineRunner {
	@Autowired private JobLauncher jobLauncher;
	@Autowired private ApplicationContext applicationContext;
	@Autowired private JobsHelper jobsHelper;
	@Override
	public void run(String... args) throws Exception {
		//Call first job
		jobsHelper.executeJob( jobLauncher, applicationContext, Constants.CLEAN_UP_WORKING_DIRECTORY_OS_JOB);
		//Call second job
		jobsHelper.executeJob(jobLauncher, applicationContext, Constants.RETRIEVE_NEWS_JOB);
		//Call third job
		jobsHelper.executeJob(jobLauncher, applicationContext, Constants.PROCESS_NEWS_JOB);
	}
}
