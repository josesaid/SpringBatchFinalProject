package com.mx.development.said.olano.configuration.jobs;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.configuration.listener.step.chunk.NewsFromCSVToCSVChunkStepListener;
import com.mx.development.said.olano.entity.News;
import com.mx.development.said.olano.entity.NewsProcess;
import lombok.RequiredArgsConstructor;
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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PersistNewsJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager txManager;
	private final ItemReader<News> newsItemReader;
	private final ItemWriter<NewsProcess> newsItemWriter;
	@Lazy private final ItemProcessor<News, NewsProcess> mySQLNewsItemProcessor;

	@Bean
	public Job persistNewsJob(){
		return new JobBuilder(Constants.MYSQL_PERSIST_NEWS_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.flow(mysqlPersistNewsStep(txManager))
				.end()
				.build();
	}

	@Bean
	public Step mysqlPersistNewsStep(PlatformTransactionManager txManager) {
		return new StepBuilder(Constants.MYSQL_PERSIST_NEWS_STEP, jobRepository)
				.<News, NewsProcess>chunk(10, txManager)
				//.listener(newsFromCSVToCSVChunkStepListener)
				//.listener(csv01ItemFileReaderListener())
				//.listener(csv01FileItemProcessListener())
				//.listener(csv01ItemFileWriterListener())
				.reader(newsItemReader)
				.processor(mySQLNewsItemProcessor)
				.writer(writer())
				//.faultTolerant()
				//.skipLimit(100)
				//.skip(MexicoFoundException.class)
				//.noSkip(NeverThrownException.class) //I never threw this exception
				.build();
	}

	@Autowired
	@Lazy private DataSource mySQLDataSource;

	/*@Bean("mySQLDataSource")
	@ConfigurationProperties("spring.datasource.mysql.hikari")
	@Lazy
	DataSource mySQLDataSource() {
		return mySQLDataSourceProperties()
				.initializeDataSourceBuilder()
				.build();
	}*/

	/*@Bean
	@ConfigurationProperties("spring.datasource.mysql")
	@Lazy
	DataSourceProperties mySQLDataSourceProperties() {
		return new DataSourceProperties();
	}*/

	@Bean
	JdbcBatchItemWriter<NewsProcess> writer() {
		log.info("JdbcBatchItemWriter...");
		JdbcBatchItemWriter<NewsProcess> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(mySQLDataSource);
		String insertStatement = "insert into NEWS (author, type, typeVersionNumber, feedTitle, category, entryTitle) " +
				 				 "VALUES (:author, :type, :typeVersionNumber, :feedTitle, :category, :entryTitle)";
		writer.setSql(insertStatement);
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.afterPropertiesSet();
		return writer;
	}

	/*
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
	*/

}
