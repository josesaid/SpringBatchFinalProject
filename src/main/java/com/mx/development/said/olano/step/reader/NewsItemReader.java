package com.mx.development.said.olano.step.reader;

import com.mx.development.said.olano.commons.Constants;
import com.mx.development.said.olano.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@EnableBatchProcessing
@Configuration
public class NewsItemReader {

	@Value("${processedCSVFileInput}")
	private String inputCSVFile;

	@Bean
	FlatFileItemReader<News> flatFileItemReader(@Value("${processedCSVFileInput}") Resource newsCSVFileOutput){
		FlatFileItemReader<News> flatFileItemReader = new FlatFileItemReader<>();
		Resource resource = new FileSystemResource(inputCSVFile);
		log.info("flatFileItemReader -inputFile {}",inputCSVFile);
		flatFileItemReader.setName("News");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setLineMapper(newsLineMapper());
		return flatFileItemReader;
	}


	@Bean
	LineMapper<News> newsLineMapper() {
		DefaultLineMapper<News> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames(Constants.HEADERS_CSV_FILE_01.split(","));
		lineTokenizer.setStrict(false); // Set strict property to false

		fieldSetMapper.setTargetType(News.class);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

}
