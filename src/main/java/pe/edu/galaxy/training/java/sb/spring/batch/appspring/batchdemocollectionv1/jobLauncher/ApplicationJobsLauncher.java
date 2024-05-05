package pe.edu.galaxy.training.java.sb.spring.batch.appspring.batchdemocollectionv1.jobLauncher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationJobsLauncher implements CommandLineRunner {

	private final JobLauncher jobLauncher;
	private final ApplicationContext applicationContext;

	public ApplicationJobsLauncher(JobLauncher jobLauncher, ApplicationContext applicationContext) {
		this.jobLauncher = jobLauncher;
		this.applicationContext = applicationContext;
	}

	@Override
	public void run(String... args) throws Exception {
		//Call first job
		Job retrieveNewsJob = (Job) applicationContext.getBean("retrieveNewsJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("JobID", uniqueJobId()).toJobParameters();
		JobExecution jobExecution = jobLauncher.run(retrieveNewsJob, jobParameters);
		BatchStatus batchStatus = jobExecution.getStatus();
		while (batchStatus.isRunning()) {
			log.info("Job en ejecución...");
			Thread.sleep(5000L);
		}

		/*
		//Call second job
		Job persistNewsAtCSVFileJob = (Job) applicationContext.getBean("persistNewsAtCSVFileJob");
		JobParameters persistCSVJobParameters = new JobParametersBuilder().addString("JobID", uniqueJobId()).toJobParameters();
		JobExecution persistNewsAtCSVFileJobExecution = jobLauncher.run(persistNewsAtCSVFileJob, persistCSVJobParameters);
		BatchStatus persistBatchStatus = persistNewsAtCSVFileJobExecution.getStatus();
		*/
	}

	private String uniqueJobId(){
		return String.valueOf(System.currentTimeMillis());
	}

}
