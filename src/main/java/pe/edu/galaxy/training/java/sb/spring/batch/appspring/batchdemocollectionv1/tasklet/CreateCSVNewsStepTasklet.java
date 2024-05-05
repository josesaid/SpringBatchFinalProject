package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.News;

import java.io.PrintWriter;
import java.util.List;

@Slf4j
public class CreateCSVNewsStepTasklet implements Tasklet {

    @Value( "${newsCSVFileOutput}" )
    private String newsCSVFileOutput;
    private List<News> newsList;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        //TODO: Refactor this code
        this.newsList = RetrieveNewsStepTasklet.newsList;

        String newsCSVFileOutput = "/Users/josesaidolanogarcia/temp/output.csv";
        PrintWriter writer = new PrintWriter(newsCSVFileOutput);
        writer.println("id,author,type,typeVersionNumber,feedTitle,category,entryTitle");

        for (News news : newsList) {
            writer.println(news.toString());
        }
        writer.close();
        log.info("output file created at: " + newsCSVFileOutput);


        return RepeatStatus.FINISHED;
    }

}
