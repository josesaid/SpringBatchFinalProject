package com.mx.development.said.olano.listener.step.read;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class Csv01FileItemReaderListener<News> implements ItemReadListener<News> {
    public void beforeRead(){
        log.info(Csv01FileItemReaderListener.class.getSimpleName()+ " BEFORE READING- Reading an item by item");
    }
    public void afterRead(News item){
        log.info(Csv01FileItemReaderListener.class.getSimpleName()+ " AFTER READING- Reading an item by item");
    }
    public void onReadError(Exception ex){
        log.error(Csv01FileItemReaderListener.class.getSimpleName()+ " IN CASE OF ERROR- Reading an item by item");
    }

}
