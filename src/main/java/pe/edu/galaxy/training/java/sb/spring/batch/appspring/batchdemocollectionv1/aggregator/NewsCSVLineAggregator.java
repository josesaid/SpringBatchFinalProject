package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.aggregator;

import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.NewsProcess;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.extractor.NewsCSVFieldExtractor;

public class NewsCSVLineAggregator extends DelimitedLineAggregator<NewsProcess> {

    public NewsCSVLineAggregator(){
        setDelimiter(",");
        setFieldExtractor(new NewsCSVFieldExtractor());
    }

    public String aggregate(NewsProcess item) {
        return super.aggregate(item);
    }

}
