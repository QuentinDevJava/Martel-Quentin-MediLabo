package com.medilabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.medilabo.ui.dto.NoteDto;
import com.medilabo.ui.dto.PatientDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/patients/notes")
public class NoteUiController {

    private final RestClient restClient;

    @Value("${note.api.url}")
    private String noteApiUrl;

    @Value("${patient.api.url}")
    private String patientApiUrl;

    @GetMapping("/add/{patientId}")
    public String addNoteForm(@PathVariable int patientId, Model model) {
	log.info("UI GET /patients/notes/add/{} - displaying add note form", patientId);
	model.addAttribute("patientNom", patientId);
	return "add-note";
    }

    @PostMapping("/add/{patientId}")
    public String submitAddNote(HttpSession session, @PathVariable int patientId,
	    @RequestParam("note") String noteText) {
	log.info("UI POST /patients/notes/add/{} - submitting note: {}", patientId, noteText);

	String token = (String) session.getAttribute("token");
	String username = (String) session.getAttribute("username");

	PatientDto patientDto = restClient.get()

		.uri(patientApiUrl + patientId)

		.header("Authorization", "Bearer " + token)

		.header("X-Username", username)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to retrieve patient ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), patientId, response.getStatusCode());
		    throw new RuntimeException("Error retrieving patient data");
		})

		.body(PatientDto.class);

	NoteDto noteDto = new NoteDto();
	noteDto.setPatientId(patientId);
	noteDto.setPatientNom(patientDto.getNom());
	noteDto.setContenuNote(noteText);

	restClient.post()

		.uri(noteApiUrl)

		.body(noteDto)

		.header("Authorization", "Bearer " + token)

		.header("X-Username", username)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to create note for patient ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), patientId, response.getStatusCode());
		    throw new IllegalStateException("Error adding note");
		})

		.toBodilessEntity();

	return "redirect:/patients";
    }

    @GetMapping("/update/{noteId}")
    public String updateNoteForm(HttpSession session, @PathVariable String noteId, Model model) {
	log.info("UI GET /patients/notes/update/{} - displaying note update form", noteId);

	String token = (String) session.getAttribute("token");
	String username = (String) session.getAttribute("username");

	NoteDto noteDto = restClient.get().uri(noteApiUrl + noteId)

		.header("Authorization", "Bearer " + token)

		.header("X-Username", username)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {
		    log.error("Request to {} {} failed to retrieve note ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), noteId, response.getStatusCode());
		    throw new RuntimeException("Error retrieving note");

		})

		.body(NoteDto.class);

	model.addAttribute("note", noteDto);

	return "update-note";
    }

    @PostMapping("/update/{noteId}")
    public String submitEditNote(HttpSession session, @PathVariable String noteId,
	    @RequestParam("note") String updatedText) {
	log.info("UI POST /patients/notes/update/{} - updating note with new content", noteId);

	String token = (String) session.getAttribute("token");
	String username = (String) session.getAttribute("username");

	NoteDto noteDto = restClient.get()

		.uri(noteApiUrl + noteId)

		.header("Authorization", "Bearer " + token)

		.header("X-Username", username)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {

		    log.error("Request to {} {} failed to retrieve note ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), noteId, response.getStatusCode());

		    throw new RuntimeException("Error retrieving note");
		})

		.body(NoteDto.class);

	noteDto.setContenuNote(updatedText);

	restClient.put()

		.uri(noteApiUrl + noteId)

		.body(noteDto)

		.header("Authorization", "Bearer " + token)

		.header("X-Username", username)

		.retrieve()

		.onStatus(HttpStatusCode::isError, (request, response) -> {

		    log.error("Request to {} {} failed to update note ID {} - HTTP {}", request.getMethod(),
			    request.getURI(), noteId, response.getStatusCode());

		    throw new RuntimeException("Error updating note");
		})

		.toBodilessEntity();

	return "redirect:/patients";
    }
}