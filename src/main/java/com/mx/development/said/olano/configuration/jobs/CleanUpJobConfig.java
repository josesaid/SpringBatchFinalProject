package com.mx.development.said.olano.configuration.jobs;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.configuration.listener.job.CleanUpWorkingDirectoryListener;
import com.mx.development.said.olano.configuration.tasklet.DeleteExistingFilesStepTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CleanUpJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    //Job Listeners:
    private final CleanUpWorkingDirectoryListener cleanUpWorkingDirectoryListener;
    @Bean
    public Job cleanUpWorkingDirectoryOSJob () {
        return new JobBuilder(Constants.CLEAN_UP_WORKING_DIRECTORY_OS_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(cleanUpWorkingDirectoryListener)
                .flow(deleteExistingFilesStep(txManager))
                .end()
                .build();
    }
    @Bean
    public Step deleteExistingFilesStep(PlatformTransactionManager txManager) {
        return new StepBuilder(Constants.DELETE_EXISTING_FILES_STEP, jobRepository)
                .tasklet(new DeleteExistingFilesStepTasklet(), txManager)
                .build();
    }
}
