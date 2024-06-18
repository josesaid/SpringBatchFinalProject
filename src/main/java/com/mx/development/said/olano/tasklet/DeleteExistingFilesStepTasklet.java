package com.mx.development.said.olano.tasklet;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.IOException;

@Slf4j
@Data
public class DeleteExistingFilesStepTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        String directory  = "/Users/josesaidolanogarcia/temp/";
        FileUtils.cleanDirectory(new File(directory));
        log.warn("Directory " + directory + " has been cleaned-up");
        return RepeatStatus.FINISHED;
    }

}
