package com.mx.development.said.olano.aggregator;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.entity.NewsProcess;
import com.mx.development.said.olano.extractor.NewsCSVFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;

public class NewsCSVLineAggregator extends DelimitedLineAggregator<NewsProcess> {

    public NewsCSVLineAggregator(){
        setDelimiter(Constants.COMMA_SEPARATOR);
        setFieldExtractor(new NewsCSVFieldExtractor());
    }

    public String aggregate(NewsProcess item) {
        return super.aggregate(item);
    }

}
