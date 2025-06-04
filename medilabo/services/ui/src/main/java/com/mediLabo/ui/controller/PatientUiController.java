package com.mediLabo.ui.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.mediLabo.ui.dto.PatientDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PatientUiController {

	private final RestTemplate restTemplate = new RestTemplate();
	private String patientApiUrl = "http://localhost:5005/api/patients/";

	@GetMapping("/patients")
	public String listPatients(Model model) {
		log.info("UI GET /patients - retrieving list of patients");
		PatientDto[] allPatient = restTemplate.getForObject(patientApiUrl, PatientDto[].class);
		model.addAttribute("patients", Arrays.asList(allPatient));
		return "patients";
	}

	@GetMapping("/patients/{id}")
	public String viewPatient(@PathVariable int id, Model model) {
		log.info("UI GET /patients/{} - retrieving patient detail", id);
		PatientDto patient = restTemplate.getForObject(patientApiUrl + id, PatientDto.class);
		model.addAttribute("patient", patient);
		return "patient-detail";
	}

	@GetMapping("/patients/add")
	public String showAddPatientForm(Model model) {
		log.info("UI GET /patients/add - showing patient creation form");
		model.addAttribute("patient", new PatientDto());
		return "patient-add";
	}

	@PostMapping("/patients/add")
	public String addPatient(@ModelAttribute PatientDto patient) {
		log.info("UI POST /patients/add - submitting new patient: {}", patient);
		restTemplate.postForObject(patientApiUrl, patient, PatientDto.class);
		return "redirect:/patients";
	}

	@GetMapping("/patients/update/{id}")
	public String showUpdateForm(@PathVariable int id, Model model) {
		log.info("UI GET /patients/update/{} - showing update form", id);
		PatientDto patient = restTemplate.getForObject(patientApiUrl + id, PatientDto.class);

		model.addAttribute("dateAnniversaire", patient.getDateAnniversaire());

		System.out.println(patient.getDateAnniversaire());
		model.addAttribute("patient", patient);
		return "patient-update";
	}

	@PostMapping("/patients/update/{id}")
	public String updatePatient(@PathVariable int id, @ModelAttribute PatientDto patient) {
		log.info("UI POST /patients/update/{} - updating patient with date: {}", id);
		restTemplate.put(patientApiUrl + id, patient);
		return "redirect:/patients";
	}

	@GetMapping("/patients/notes/{id}")
	public String afficherNotesPatient(@PathVariable String id, Model model) {
		log.info("UI GET /patients/notes/{} - displaying notes for patient", id);
		PatientDto patient = restTemplate.getForObject(patientApiUrl + "notes/" + id, PatientDto.class);
		model.addAttribute("patient", patient);
		return "patient-notes";
	}
}
