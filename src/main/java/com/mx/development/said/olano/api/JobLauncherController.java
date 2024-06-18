package com.mx.development.said.olano.api;

import java.util.HashMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/job")
public class JobLauncherController {

	//private final Job job;
	private final JobLauncher jobLauncher;
	
	@PostMapping("/launcher")
	public ResponseEntity<?> launcher(){

		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();

			//var jobExecution = jobLauncher.run(job, jobParameters);

			//var batchStatus = jobExecution.getStatus();
			
			var ret= new HashMap<String, Object>();
			
			//ret.put("status", batchStatus);
		
			return ResponseEntity.ok().body(ret);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
			
	}
	
}
