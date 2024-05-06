package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.step.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.aggregator.NewsCSVLineAggregator;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.callback.NewsCsvFlatFileHeaderCallback;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.NewsProcess;

@Slf4j
@Configuration
public class NewsItemWriter{

	@Value("${processedCSVFileOutput}")
	private String processedCSVFileOutput;

	@Bean
	FlatFileItemWriter<NewsProcess> flatFileItemWriter() {
		log.info("write processedCSVFileOutput: " + processedCSVFileOutput);

		FlatFileItemWriter<NewsProcess> writer = new FlatFileItemWriter<>();
		WritableResource resource = new FileSystemResource(processedCSVFileOutput);
		writer.setResource(resource);
		writer.setAppendAllowed(true);
		writer.setLineAggregator(new NewsCSVLineAggregator());
		writer.setHeaderCallback(new NewsCsvFlatFileHeaderCallback());


		return writer;
	}

}
