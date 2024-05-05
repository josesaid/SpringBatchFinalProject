package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.step.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TallerWriter implements ItemWriter<String>{

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
	
		for (String taller : chunk) {
			System.out.println("Write data " + taller);
		}
		
	}

}
