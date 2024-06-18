package com.mx.development.said.olano.tasklet;

import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class CreateCSVNewsStepTasklet implements Tasklet {

    private List<News> newsList;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //TODO: Refactor this code
        //this.newsList = RetrieveNewsStepTasklet.newsList;

        //First job will save to newsCSVFileOutput
        String newsCSVFileOutput = "/Users/josesaidolanogarcia/temp/cleanup_data_v1.csv";
        PrintWriter writer = new PrintWriter(newsCSVFileOutput);

        writer.println("id,author,type,typeVersionNumber,feedTitle,category,entryTitle");

        final String COMMA = ",";
        int counter = 1;
        for (News news : RetrieveNewsStepTasklet.newsList) {
            String linea = new StringBuffer(counter++).append(COMMA)
                    .append(news.getAuthor()).append(COMMA)
                    .append(news.getType()).append(COMMA)
                    .append(news.getTypeVersionNumber()).append(COMMA)
                    .append(news.getFeedTitle()).append(COMMA)
                    .append(news.getCategory()).append(COMMA)
                    .append(news.getEntryTitle()).append(COMMA)
                    .toString();
            writer.println(linea);
        }
        writer.close();
        log.info("output file created at: " + newsCSVFileOutput);

        return RepeatStatus.FINISHED;
    }

}
