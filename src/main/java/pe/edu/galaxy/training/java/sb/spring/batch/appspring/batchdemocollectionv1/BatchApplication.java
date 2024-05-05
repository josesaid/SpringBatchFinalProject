package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BatchApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.profiles("dev") // and so does this
				.sources(BatchApplication.class)
				.run(args);
	}

}
