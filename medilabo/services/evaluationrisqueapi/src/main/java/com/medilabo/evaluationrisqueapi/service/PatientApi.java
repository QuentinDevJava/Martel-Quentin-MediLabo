package com.medilabo.evaluationrisqueapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;

@FeignClient(name = "patientapi")
public interface PatientApi {

	@GetMapping("api/patients/name/{patientName}")
	PatientDto getPatientDto(@PathVariable String patientName);

}