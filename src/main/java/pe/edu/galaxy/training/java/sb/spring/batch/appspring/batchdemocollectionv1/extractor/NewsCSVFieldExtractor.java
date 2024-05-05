package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.extractor;

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.News;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.NewsProcess;

public class NewsCSVFieldExtractor extends BeanWrapperFieldExtractor<NewsProcess> {

    public NewsCSVFieldExtractor(){
        setNames(NewsProcess.getNames());
    }

}
