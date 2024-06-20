package com.mx.development.said.olano.configuration.jobs;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.configuration.listener.job.RetrieveNewsListener;
import com.mx.development.said.olano.configuration.listener.step.execution.RetrieveNewsStepExecutionListener;
import com.mx.development.said.olano.configuration.tasklet.CleanUpAndFormatNewsStepTasklet;
import com.mx.development.said.olano.configuration.tasklet.CreateCSVNewsStepTasklet;
import com.mx.development.said.olano.configuration.tasklet.RetrieveNewsStepTasklet;
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
public class RetrieveNewsJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    //Job Listeners:
    private final RetrieveNewsListener retrieveNewsListener;

    //(Step) Execution Listener:
    private final RetrieveNewsStepExecutionListener retrieveNewsStepExecutionListener;

    @Bean
    public Job retrieveNewsJob() {
        return new JobBuilder(Constants.RETRIEVE_NEWS_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(retrieveNewsListener)
                .flow(retrieveNewsStep(txManager))
                .next(cleanUpAndFormatNewsStep(txManager))
                .next(createCSVNewsStep(txManager))
                .end()
                .build();
    }

    @Bean
    public Step retrieveNewsStep(PlatformTransactionManager txManager) {
        return new StepBuilder(Constants.RETRIEVE_NEWS_STEP, jobRepository)
                .listener(retrieveNewsStepExecutionListener)
                .tasklet(new RetrieveNewsStepTasklet(), txManager)
                .build();
    }

    @Bean
    public Step cleanUpAndFormatNewsStep(PlatformTransactionManager txManager) {
        return new StepBuilder(Constants.CLEAN_UP_AND_FORMAT_NEWS_STEP, jobRepository)
                .tasklet(new CleanUpAndFormatNewsStepTasklet(), txManager)
                .build();
    }

    @Bean
    public Step createCSVNewsStep(PlatformTransactionManager txManager) {
        return new StepBuilder(Constants.CREATE_CSV_NEWS_STEP, jobRepository)
                .tasklet(new CreateCSVNewsStepTasklet(), txManager)
                .build();
    }

}
