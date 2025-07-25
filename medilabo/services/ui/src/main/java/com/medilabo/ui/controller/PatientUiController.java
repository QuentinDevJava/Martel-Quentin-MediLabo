package com.medilabo.ui.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;

import com.medilabo.ui.dto.PatientDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PatientUiController {

    private final RestClient restClient;

    @Value("${patient.api.url}")
    private String patientApiUrl;

    @GetMapping("/patients")
    public String listOfAllPatients(HttpSession session, Model model) {
	log.info("UI GET /patients - retrieving list of patients");

	String token = (String) session.getAttribute("token");

	PatientDto[] patients = restClient.get().uri(patientApiUrl + "/notes")

		.header("Authorization", "Bearer " + token)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to fetch patients list - HTTP {}", request.getMethod(),
			    request.getURI(), response.getStatusCode());
		    throw new RuntimeException("Failed to retrieve patient list - "
			    + response.getStatusCode().toString() + " - " + response.getStatusText());
		})

		.body(PatientDto[].class);

	model.addAttribute("patients", Arrays.asList(patients));
	return "patients";
    }

    @GetMapping("/patients/{id}")
    public String viewPatientDetail(@PathVariable int id, Model model, HttpSession session) {
	log.info("UI GET /patients/{} - retrieving patient detail", id);

	String token = (String) session.getAttribute("token");

	PatientDto patientDto = restClient.get()

		.uri(patientApiUrl + id)

		.header("Authorization", "Bearer " + token)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to retrieve patient ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), id, response.getStatusCode());
		    throw new RuntimeException(
			    "Failed to retrieve patient details - " + response.getStatusCode().toString());
		})

		.body(PatientDto.class);

	if (patientDto == null) {
	    throw new RuntimeException("Patient not found");
	}

	model.addAttribute("patient", patientDto);
	return "patient-detail";
    }

    @GetMapping("/patients/add")
    public String showAddPatientForm(Model model) {
	log.info("UI GET /patients/add - showing patient creation form");
	model.addAttribute("patient", new PatientDto());
	return "patient-add";
    }

    @PostMapping("/patients/add")
    public String addPatient(@ModelAttribute PatientDto patient, HttpSession session) {
	log.info("UI POST /patients/add - submitting new patient: {}", patient);

	String token = (String) session.getAttribute("token");

	restClient.post().uri(patientApiUrl)

		.body(patient)

		.header("Authorization", "Bearer " + token)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to add patient - HTTP {}", request.getMethod(), request.getURI(),
			    response.getStatusCode());
		    throw new RuntimeException("failed to add patient - " + response.getStatusCode().toString());
		})

		.toBodilessEntity();

	return "redirect:/patients";
    }

    @GetMapping("/patients/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model, HttpSession session) {
	log.info("UI GET /patients/update/{} - showing update form", id);

	String token = (String) session.getAttribute("token");

	PatientDto patientDto = restClient.get().uri(patientApiUrl + id)

		.header("Authorization", "Bearer " + token)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to fetch patient for update ID {} - HTTP {}",
			    request.getMethod(), request.getURI(), id, response.getStatusCode());
		    throw new RuntimeException("Failed to fetch patient data - " + response.getStatusCode().toString());
		}).body(PatientDto.class);

	if (patientDto == null) {
	    throw new RuntimeException("Patient not found");
	}

	model.addAttribute("patient", patientDto);
	model.addAttribute("dateAnniversaire", patientDto.getDateAnniversaire());
	return "patient-update";
    }

    @PostMapping("/patients/update/{id}")
    public String updatePatient(@PathVariable int id, @ModelAttribute PatientDto patient, HttpSession session) {
	log.info("UI POST /patients/update/{} - updating patient", id);

	String token = (String) session.getAttribute("token");

	restClient.put().uri(patientApiUrl + id)

		.body(patient)

		.header("Authorization", "Bearer " + token)

		.retrieve().onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to update patient ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), id, response.getStatusCode());
		    throw new RuntimeException("Failed to update patient - " + response.getStatusCode().toString());
		})

		.toBodilessEntity();

	return "redirect:/patients";
    }

    @GetMapping("/patients/notes/{id}")
    public String viewPatientNotes(@PathVariable String id, Model model, HttpSession session) {
	log.info("UI GET /patients/notes/{} - displaying notes for patient", id);

	String token = (String) session.getAttribute("token");

	PatientDto patientDto = restClient.get()

		.uri(patientApiUrl + "notes/" + id)

		.header("Authorization", "Bearer " + token)

		.retrieve().onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to retrieve notes for patient ID {} - HTTP {}",
			    request.getMethod(), request.getURI(), id, response.getStatusCode());
		    throw new RuntimeException(
			    "Failed to retrieve patient notes - " + response.getStatusCode().toString());
		})

		.body(PatientDto.class);

	if (patientDto == null) {
	    throw new RuntimeException("Patient not found");
	}

	model.addAttribute("patient", patientDto);
	return "patient-notes";
    }
}