package com.mx.development.said.olano;

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
