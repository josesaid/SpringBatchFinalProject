package com.mx.development.said.olano.configuration.jobs;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.exception.MexicoFoundException;
import com.mx.development.said.olano.exception.NeverThrownException;
import com.mx.development.said.olano.configuration.listener.job.PrepareNewsToCSVFile01Listener;
import com.mx.development.said.olano.configuration.listener.step.chunk.NewsFromCSVToCSVChunkStepListener;
import com.mx.development.said.olano.configuration.listener.step.process.Csv01FileItemProcessListener;
import com.mx.development.said.olano.configuration.listener.step.read.Csv01FileItemReaderListener;
import com.mx.development.said.olano.configuration.listener.step.write.Csv01FileItemWriterListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import lombok.RequiredArgsConstructor;
import com.mx.development.said.olano.entity.News;
import com.mx.development.said.olano.entity.NewsProcess;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProcessNewsJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager txManager;
	private final ItemReader<News> newsItemReader;
	private final ItemWriter<NewsProcess> newsItemWriter;
	private final ItemProcessor<News, NewsProcess> newsItemProcessor;

	//Job Listeners:
	private final PrepareNewsToCSVFile01Listener prepareNewsToCSVFile01Listener;

	//Chunk (Step) Listener:
	private final NewsFromCSVToCSVChunkStepListener newsFromCSVToCSVChunkStepListener;

	@Bean
	public Job processNewsJob(){
		return new JobBuilder(Constants.NEWS_PROCESS_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(prepareNewsToCSVFile01Listener)
				.flow(newsProcessStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step newsProcessStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.NEWS_PROCESS_STEP, jobRepository)
				.<News, NewsProcess>chunk(10, txManager)
				.listener(newsFromCSVToCSVChunkStepListener)
				.listener(csv01ItemFileReaderListener())
				.listener(csv01FileItemProcessListener())
				.listener(csv01ItemFileWriterListener())
				.reader(newsItemReader)
				.processor(newsItemProcessor)
				.writer(newsItemWriter)
				.faultTolerant()
				.skipLimit(100)
				.skip(MexicoFoundException.class)
				.noSkip(NeverThrownException.class) //I never threw this exception
				.build();
	}

	//(Step) ItemReader Listener:
	@Bean public Csv01FileItemReaderListener csv01ItemFileReaderListener(){
		return new Csv01FileItemReaderListener();
	}

	//(Step) ItemWriter Listener:
	@Bean public Csv01FileItemWriterListener csv01ItemFileWriterListener(){
		return new Csv01FileItemWriterListener();
	}

	//(Step) ItemProcess Listener:
	@Bean public Csv01FileItemProcessListener csv01FileItemProcessListener(){
		return new Csv01FileItemProcessListener();
	}

}
