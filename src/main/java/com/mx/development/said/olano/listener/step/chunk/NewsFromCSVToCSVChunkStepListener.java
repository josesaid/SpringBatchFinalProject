package com.mx.development.said.olano.listener.step.chunk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class NewsFromCSVToCSVChunkStepListener implements ChunkListener {
    /*
    public void beforeStep(StepExecution stepExecution){
        log.info(NewsFromCSVToCSVChunkStepListener.class.getSimpleName()+" [beforeStep] has been called only for DEMO purposes at: " + LocalDateTime.now() );
    }

    public ExitStatus afterStep(StepExecution stepExecution){
        log.info(NewsFromCSVToCSVChunkStepListener.class.getSimpleName()+" [afterStep] has been completed only for DEMO purposes at: " + LocalDateTime.now() );
        return ExitStatus.COMPLETED;
    }*/

    public void beforeChunk(ChunkContext context){
        log.info("Job [" + context.getStepContext().getJobName() + "] >> Step ["+ context.getStepContext().getStepName()+"] has started " +
                "at: " + LocalDateTime.now());
        log.info("BeforeChunk method - Antes de procesar el chunk");
    }
    public void afterChunk(ChunkContext context){
        log.info("Job [" + context.getStepContext().getJobName() + "] >> Step ["+ context.getStepContext().getStepName()+"] has started " +
                "at: " + LocalDateTime.now());
        log.info("AfterChunk method - Despues de procesar el chunk");
    }
    public void afterChunkError(ChunkContext context){
        log.info("Job [" + context.getStepContext().getJobName() + "] >> Step ["+ context.getStepContext().getStepName()+"] has started " +
                "at: " + LocalDateTime.now());
        log.info("AfterChunk Error method - En caso de error en la gestión del chunk, veremos este método");
    }

}
