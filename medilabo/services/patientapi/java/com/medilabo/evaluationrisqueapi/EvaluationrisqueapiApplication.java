package com.medilabo.evaluationrisqueapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EvaluationrisqueapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvaluationrisqueapiApplication.class, args);
	}

}
