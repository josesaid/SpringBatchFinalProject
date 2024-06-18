package com.mx.development.said.olano.tasklet;

import com.mx.development.said.olano.entity.News;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class CleanUpNewsStepTasklet implements Tasklet {
    private List<News> newsList;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        //TODO: Refactor this code
        this.newsList = RetrieveNewsStepTasklet.newsList;

        newsList.stream().forEach(news -> System.out.println(news));
        System.out.println("-------------------------------------------------------");
        System.out.println("Clean up process.... coming up:");
        System.out.println("-------------------------------------------------------");

        newsList.stream().forEach(news -> news.setAuthor(news.getAuthor()==null? " Desconocido" : news.getAuthor() ) );
        newsList.stream().forEach(news -> news.setType(news.getType().endsWith("2.0")? "RSS": news.getType() ));

        newsList.stream().forEach(news -> System.out.println(news));

        //TODO: Refactor this code
        RetrieveNewsStepTasklet.newsList = newsList;

        return RepeatStatus.FINISHED;
    }


}
