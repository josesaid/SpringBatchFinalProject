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
		Job executeOSJob = (Job) applicationContext.getBean("executeOSJob");
		JobParameters jobParameters = new JobParametersBuilder().addString("JobID", uniqueJobId()).toJobParameters();
		JobExecution jobOSExecution = jobLauncher.run(executeOSJob, jobParameters);
		BatchStatus batchStatus = jobOSExecution.getStatus();
		log.info("Finished:" + String.valueOf(batchStatus.isRunning()));

		//Call second job
		Job retrieveNewsJob = (Job) applicationContext.getBean("retrieveNewsJob");
		JobParameters secondJobParameters = new JobParametersBuilder().addString("JobID", uniqueJobId()).toJobParameters();
		JobExecution jobExecution = jobLauncher.run(retrieveNewsJob, secondJobParameters);
		BatchStatus secondBatchStatus = jobExecution.getStatus();
		log.info("Finished:" + String.valueOf(secondBatchStatus.isRunning()));

		//Call third job
		Job newsProcessJob = (Job) applicationContext.getBean("newsProcessJob");
		JobParameters newsProcessJobParameters = new JobParametersBuilder().addString("JobID", uniqueJobId()).toJobParameters();
		JobExecution newsProcessJobExecution = jobLauncher.run(newsProcessJob, newsProcessJobParameters);
		BatchStatus persistBatchStatus = newsProcessJobExecution.getStatus();
		log.info("Finished:" + String.valueOf(persistBatchStatus.isRunning()));

	}

	private String uniqueJobId(){
		return String.valueOf(System.currentTimeMillis());
	}

}
