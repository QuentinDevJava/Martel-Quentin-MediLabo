package com.mediabo.patientapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediabo.patientapi.dto.PatientDto;
import com.mediabo.patientapi.service.PatientService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/patients/")
@AllArgsConstructor
public class PatientController {

	private PatientService patientService;

	@GetMapping("{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable int id) {
		log.info("Receive GET /api/patients/" + id + ": PatientApi use RestController to send PatientDto by ID");
		return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		log.info("Receive GET /api/patients: PatientApi use RestController to send list of PatientDto");
		return new ResponseEntity<>(patientService.getAllPatient(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PatientDto> addPatient(@Valid @RequestBody PatientDto patientDto) {
		PatientDto savedPatient = patientService.addPatient(patientDto);
		log.info("Receive POST /api/patients: PatientDto " + patientDto
				+ " - PatientApi use RestController to create Patient");
		return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable int id, @Valid @RequestBody PatientDto patientDto) {
		PatientDto updatedPatient = patientService.updatePatient(id, patientDto);
		log.info("Receive PUT /api/patients/" + id + ": PatientDto " + patientDto
				+ " - PatientApi use RestController to update Patient");
		return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
	}

	@GetMapping("notes/{id}")
	public ResponseEntity<PatientDto> getPatientWithNotesById(@PathVariable int id) {
		log.info("Receive GET /api/patients/" + id + ": PatientApi use RestController to send PatientDto by ID");
		return new ResponseEntity<>(patientService.getPatientWithNotesById(id), HttpStatus.OK);
	}

	@GetMapping("notes")
	public ResponseEntity<List<PatientDto>> getAllPatientWithNotesById() {
		log.info("Receive GET /api/patients/notes - PatientApi use RestController to send all Patients with notes");
		return new ResponseEntity<>(patientService.getAllPatientsWithNotes(), HttpStatus.OK);
	}

}
