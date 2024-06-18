package com.mx.development.said.olano.tasklet;

import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

@Slf4j
public class CleanUpAndFormatNewsStepTasklet implements Tasklet {
    private List<News> newsList;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        //TODO: Refactor this code
        this.newsList = RetrieveNewsStepTasklet.newsList;

        newsList.stream().forEach(news -> System.out.println(news));
        log.info("-------------------------------------------------------");
        log.info("Clean up and format news process - It is coming up:");
        log.info("-------------------------------------------------------");

        newsList.stream().forEach(news -> news.setAuthor(news.getAuthor()==null? " Desconocido" : news.getAuthor() ) );
        newsList.stream().forEach(news -> news.setType(news.getType().endsWith("2.0")? "RSS": news.getType() ));

        newsList.stream().forEach(news -> System.out.println(news));

        //TODO: Refactor this code
        RetrieveNewsStepTasklet.newsList = newsList;

        return RepeatStatus.FINISHED;
    }


}
