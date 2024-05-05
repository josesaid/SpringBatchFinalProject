package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.step.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class TallerReader implements ItemReader<String> {

	private String[] talleres = 
			{ 
			"File PDF Loand & Unload in Angular 17", "Java Frameworks for API RESTful",
			"Primeros pasos con Terraform",
			"Spring Batch - introductions"
			
			};

	private int count = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


		if (count < talleres.length) {
			return talleres[count++];
		}
		return null;
	}

}
