package com.mx.development.said.olano.job;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.exception.MexicoFoundException;
import com.mx.development.said.olano.exception.NeverThrownException;
import com.mx.development.said.olano.listener.job.CleanUpWorkingDirectoryListener;
import com.mx.development.said.olano.listener.job.PrepareNewsToCSVFile01Listener;
import com.mx.development.said.olano.listener.job.RetrieveNewsListener;
import com.mx.development.said.olano.listener.step.chunk.NewsFromCSVToCSVChunkStepListener;
import com.mx.development.said.olano.listener.step.execution.RetrieveNewsStepExecutionListener;
import com.mx.development.said.olano.listener.step.process.Csv01FileItemProcessListener;
import com.mx.development.said.olano.listener.step.read.Csv01FileItemReaderListener;
import com.mx.development.said.olano.listener.step.write.Csv01FileItemWriterListener;
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
import com.mx.development.said.olano.tasklet.CleanUpAndFormatNewsStepTasklet;
import com.mx.development.said.olano.tasklet.CreateCSVNewsStepTasklet;
import com.mx.development.said.olano.tasklet.DeleteExistingFilesStepTasklet;
import com.mx.development.said.olano.tasklet.RetrieveNewsStepTasklet;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NewsConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager txManager;
	private final ItemReader<News> newsItemReader;
	private final ItemWriter<NewsProcess> newsItemWriter;
	private final ItemProcessor<News, NewsProcess> newsItemProcessor;

	//Job Listeners:
	private final CleanUpWorkingDirectoryListener cleanUpWorkingDirectoryListener;
	private final RetrieveNewsListener retrieveNewsListener;
	private final PrepareNewsToCSVFile01Listener prepareNewsToCSVFile01Listener;

	//(Step) Execution Listener:
	private final RetrieveNewsStepExecutionListener retrieveNewsStepExecutionListener;

	//Chunk (Step) Listener:
	private final NewsFromCSVToCSVChunkStepListener newsFromCSVToCSVChunkStepListener;

	@Bean
	public Job cleanUpWorkingDirectoryOSJob () {
		return new JobBuilder(Constants.CLEAN_UP_WORKING_DIRECTORY_OS_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(cleanUpWorkingDirectoryListener)
				.flow(deleteExistingFilesStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step deleteExistingFilesStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.DELETE_EXISTING_FILES_STEP, jobRepository)
				.tasklet(new DeleteExistingFilesStepTasklet(), txManager)
				.build();
	}

	@Bean
	public Job retrieveNewsJob() {
		return new JobBuilder(Constants.RETRIEVE_NEWS_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(retrieveNewsListener)
				.flow(retrieveNewsStep(txManager))
				.next(cleanUpAndFormatNewsStep(txManager))
				.next(createCSVNewsStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step retrieveNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.RETRIEVE_NEWS_STEP, jobRepository)
				.listener(retrieveNewsStepExecutionListener)
				.tasklet(new RetrieveNewsStepTasklet(), txManager)
				.build();
	}

	@Bean
	public Step cleanUpAndFormatNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.CLEAN_UP_AND_FORMAT_NEWS_STEP, jobRepository)
				.tasklet(new CleanUpAndFormatNewsStepTasklet(), txManager)
				.build();
	}

	@Bean
	public Step createCSVNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.CREATE_CSV_NEWS_STEP, jobRepository)
				.tasklet(new CreateCSVNewsStepTasklet(), txManager)
				.build();
	}

	@Bean
	public Job newsProcessJob(){
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
