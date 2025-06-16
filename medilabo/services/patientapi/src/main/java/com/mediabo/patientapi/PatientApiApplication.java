package com.mediabo.patientapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PatientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApiApplication.class, args);
	}

}
