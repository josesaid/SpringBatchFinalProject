package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.step.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.News;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.NewsProcess;

@Component
public class NewsItemProcessor implements ItemProcessor<News, NewsProcess>{


	@Override
	public NewsProcess process(News item) throws Exception {
		NewsProcess newsProcess = new NewsProcess();
		BeanUtils.copyProperties(item,newsProcess);
		return newsProcess;
	}

}
