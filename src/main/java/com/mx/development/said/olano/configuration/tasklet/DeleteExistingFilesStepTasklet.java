package com.mx.development.said.olano.configuration.tasklet;

import com.mx.development.said.olano.commons.Constants;
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
        FileUtils.cleanDirectory(new File(Constants.WORKING_DIRECTORY));
        log.warn("Directory " + Constants.WORKING_DIRECTORY + " has been cleaned-up");
        return RepeatStatus.FINISHED;
    }

}
