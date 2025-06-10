package com.mediLabo.patientapi.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediLabo.patientapi.dto.NoteDto;

@FeignClient(name = "noteapi")
public interface NoteApi {

	@GetMapping("api/notes/patient/{patientId}")
	List<NoteDto> getNoteDtos(@PathVariable int patientId);
}