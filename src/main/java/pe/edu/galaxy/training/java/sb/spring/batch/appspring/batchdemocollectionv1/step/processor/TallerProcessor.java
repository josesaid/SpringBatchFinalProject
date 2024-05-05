package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.step.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TallerProcessor implements ItemProcessor<String, String>{

	@Override
	public String process(String item) throws Exception {
		
		return item.toUpperCase();
		
		
	}

}
