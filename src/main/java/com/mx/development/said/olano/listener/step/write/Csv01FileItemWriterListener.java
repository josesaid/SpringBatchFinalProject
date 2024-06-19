package com.mx.development.said.olano.listener.step.write;

import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class Csv01FileItemWriterListener implements ItemWriteListener<News> {
    public void beforeWrite(List<? extends News> items){
        log.info(Csv01FileItemWriterListener.class.getSimpleName()+ " BEFORE WRITING - Writing a list of items:" + items);
    }
    public void afterWrite(List<? extends News> items){
        log.info(Csv01FileItemWriterListener.class.getSimpleName()+ " AFTER WRITING - Writing a list of items: " + items);
    }
    public void onWriteError(Exception exception, List<? extends News> items){
        log.info(Csv01FileItemWriterListener.class.getSimpleName()+ "One error was found processing these NEWS: " + items);
        log.info(Csv01FileItemWriterListener.class.getSimpleName()+ "And the ERROR is: " + exception.getMessage());
    }

}
