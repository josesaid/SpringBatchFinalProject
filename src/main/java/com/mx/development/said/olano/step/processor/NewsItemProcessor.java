package com.mx.development.said.olano.step.processor;

import com.mx.development.said.olano.commons.Constants;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.mx.development.said.olano.entity.News;
import com.mx.development.said.olano.entity.NewsProcess;

@Component
public class NewsItemProcessor implements ItemProcessor<News, NewsProcess>{

	public int counter = 1;
	@Override
	public NewsProcess process(News item) throws Exception {

		//Ignora las noticias mal redactadas
		if(item.getEntryTitle().split(Constants.WHITE_SPACE).length <= 4)
			return null;

		NewsProcess newsProcess = new NewsProcess();
		BeanUtils.copyProperties(item,newsProcess);
		newsProcess.setId(item.getId().length()==0?String.valueOf(counter++):"TBD");
		newsProcess.setType(item.getType().endsWith("2.0")? "RSS": item.getType());
		newsProcess.setTypeVersionNumber(item.getTypeVersionNumber().length()> 0 ? "2.0":"1.0");
		newsProcess.setCategory(item.getFeedTitle().contains("Tecnología")? "Tecnología": "Categoría desconocida" );
		newsProcess.setFeedTitle(item.getFeedTitle().contains("Expansión")? "Expansión": "Fuente desconocida" );

		return newsProcess;
	}

}
