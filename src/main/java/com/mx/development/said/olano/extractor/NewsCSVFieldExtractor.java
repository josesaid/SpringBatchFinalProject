package com.mx.development.said.olano.extractor;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import com.mx.development.said.olano.entity.NewsProcess;

public class NewsCSVFieldExtractor extends BeanWrapperFieldExtractor<NewsProcess> {
    public NewsCSVFieldExtractor(){
        setNames(NewsProcess.getNames());
    }

}
