package com.medilabo.noteapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.noteapi.dto.PatientDto;

@FeignClient(name = "patientapi")
public interface PatientApi {

	@GetMapping("api/patients/{patientId}")
	PatientDto getPatientById(@PathVariable int patientId);
}