package com.medilabo.evaluationrisqueapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:5005/", name = "noteapi")
public interface NoteApi {

	@GetMapping("api/notes/termesAnalyse/{patientName}")
	int getTriggerTerms(@PathVariable String patientName);
}