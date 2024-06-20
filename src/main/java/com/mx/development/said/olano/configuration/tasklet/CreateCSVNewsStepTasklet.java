package com.mx.development.said.olano.configuration.tasklet;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import java.io.PrintWriter;

@Slf4j
public class CreateCSVNewsStepTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        PrintWriter writer = new PrintWriter(Constants.CSV_FILE_01_PATH);
        writer.println(Constants.HEADERS_CSV_FILE_01);

        int counter = 1;
        for (News news : RetrieveNewsStepTasklet.newsList) {
            String linea = new StringBuffer(counter++).append(Constants.COMMA_SEPARATOR)
                    .append(news.getAuthor()).append(Constants.COMMA_SEPARATOR)
                    .append(news.getType()).append(Constants.COMMA_SEPARATOR)
                    .append(news.getTypeVersionNumber()).append(Constants.COMMA_SEPARATOR)
                    .append(news.getFeedTitle()).append(Constants.COMMA_SEPARATOR)
                    .append(news.getCategory()).append(Constants.COMMA_SEPARATOR)
                    .append(news.getEntryTitle()).append(Constants.COMMA_SEPARATOR)
                    .toString();
            writer.println(linea);
        }
        writer.close();
        log.info("output file created at: " + Constants.CSV_FILE_01_PATH);
        return RepeatStatus.FINISHED;
    }

}
