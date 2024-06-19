package com.mx.development.said.olano.listener.step.process;

import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
@Slf4j
public class Csv01FileItemProcessListener implements ItemProcessListener<News, News> {
    public void beforeProcess(News item){
        log.info(Csv01FileItemProcessListener.class.getSimpleName()+ " BEFORE Processing the input News: " + item);
    }

    /*
    public void afterProcess(News input, News output){
        log.info(Csv01FileItemProcessListener.class.getSimpleName()+ " AFTER Processing this is the input  NEWS: " + input);
        log.info(Csv01FileItemProcessListener.class.getSimpleName()+ " AFTER Processing this is the output NEWS: " + output);
    }*/
    public void onProcessError(News item, Exception e){
        log.info(Csv01FileItemProcessListener.class.getSimpleName()+ "One error was found processing this NEWS: " + item);
        log.info(Csv01FileItemProcessListener.class.getSimpleName()+ "And the ERROR is: " + e.getMessage());
    }

}
