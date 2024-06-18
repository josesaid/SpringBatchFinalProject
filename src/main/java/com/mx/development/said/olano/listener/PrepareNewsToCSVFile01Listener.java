package com.mx.development.said.olano.listener;

import com.mx.development.said.olano.commons.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class PrepareNewsToCSVFile01Listener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(PrepareNewsToCSVFile01Listener.class.getSimpleName()+" has been executed at: " + LocalDateTime.now() );
        if (jobExecution.getStatus() == BatchStatus.STARTED) {
            log.info("La preparación de las noticias ha iniciado.");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(PrepareNewsToCSVFile01Listener.class.getSimpleName()+" has been executed at: " + LocalDateTime.now() );
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("La preparación de las noticias ha concluido.");
        }
    }

}
