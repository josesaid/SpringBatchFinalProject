package com.mx.development.said.olano.listener.step.read;

import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class CsvItemFile01ReaderListener<News> implements ItemReadListener<News> {
    public void beforeRead(){
        log.info(CsvItemFile01ReaderListener.class.getSimpleName()+ " BEFORE READING- Reading an item by item");
    }
    public void afterRead(News item){
        log.info(CsvItemFile01ReaderListener.class.getSimpleName()+ " AFTER READING- Reading an item by item");
    }
    public void onReadError(Exception ex){
        log.error(CsvItemFile01ReaderListener.class.getSimpleName()+ " IN CASE OF ERROR- Reading an item by item");
    }

}
