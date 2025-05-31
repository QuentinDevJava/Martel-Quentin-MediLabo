package com.medilabo.evaluationrisqueapi.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.evaluationrisqueapi.dto.NoteDto;

@FeignClient(url = "http://localhost:5005/", name = "noteapi")
public interface ApiClient {

	@GetMapping("api/notes/patient/{patientNom}")
	List<NoteDto> getNoteDtos(@PathVariable String patientNom);
}