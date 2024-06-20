package com.mx.development.said.olano.configuration.listener.step.execution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class RetrieveNewsStepExecutionListener implements StepExecutionListener {
    public void beforeStep(StepExecution stepExecution){
        log.info(RetrieveNewsStepExecutionListener.class.getSimpleName()+" [beforeStep] has been called only for DEMO purposes at: " + LocalDateTime.now() );
    }

    public ExitStatus afterStep(StepExecution stepExecution){
        log.info(RetrieveNewsStepExecutionListener.class.getSimpleName()+" [afterStep] has been completed only for DEMO purposes at: " + LocalDateTime.now() );
        return ExitStatus.COMPLETED;
    }

}
