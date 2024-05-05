package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.News;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.entity.NewsProcess;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.tasklet.CleanUpNewsStepTasklet;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.tasklet.CreateCSVNewsStepTasklet;
import pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.tasklet.RetrieveNewsStepTasklet;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NewsConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager txManager;
	private final ItemReader<News> newsItemReader;
	private final ItemWriter<NewsProcess> newsItemWriter;
	private final ItemProcessor<News, NewsProcess> newsItemProcessor;

	@Bean
	public Job retrieveNewsJob() {
		return new JobBuilder("retrieveNewsJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.flow(retrieveNewsStep(txManager))
				.next(cleanUpNewsStep(txManager))
				.next(createCSVNewsStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step retrieveNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder("retrieveNewsStep", jobRepository).tasklet(new RetrieveNewsStepTasklet(), txManager).build();
	}


	@Bean
	public Step cleanUpNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder("cleanUpNewsStep", jobRepository).tasklet(new CleanUpNewsStepTasklet(), txManager).build();
	}

	@Bean
	public Step createCSVNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder("createCSVNewsStep", jobRepository).tasklet(new CreateCSVNewsStepTasklet(), txManager).build();
	}

	@Bean
	public Job loadDataJob(){
		return new JobBuilder("loadDataJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.flow(loadDataStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step loadDataStep(PlatformTransactionManager txManager) {
		return new StepBuilder("stepLoadData", jobRepository)
				.<News, NewsProcess>chunk(10, txManager)
				.reader(newsItemReader)
				.processor(newsItemProcessor)
				.writer(newsItemWriter)
				.build();
	}

}
