package com.medilabo.evaluationrisqueapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "noteapi")
public interface NoteApi {

	@GetMapping("api/notes/termesAnalyse/{patientId}")
	int getTriggerTermsForPatientId(@PathVariable int patientId);
}
