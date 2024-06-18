package com.mx.development.said.olano.helpers;

import com.mx.development.said.olano.commons.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobsHelper {

    public void executeJob(JobLauncher jobLauncher, ApplicationContext applicationContext, String jobBeanName) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Job genericJob = (Job) applicationContext.getBean(jobBeanName);
        log.info("##################################################################");
        log.info("Job [" + genericJob.getName() + "] has started");
        JobParameters jobParameters = new JobParametersBuilder().addString(Constants.JOB_ID, uniqueJobId()).toJobParameters();
        JobExecution jobOSExecution = jobLauncher.run(genericJob, jobParameters);
        log.info("Job [" + genericJob.getName() + "] has finished");
        log.info("Job [" + genericJob.getName() + "] - Exit Code: "+jobOSExecution.getExitStatus().getExitCode());
        log.info("##################################################################");
    }

    private static String uniqueJobId(){
        return String.valueOf(System.currentTimeMillis());
    }

}
