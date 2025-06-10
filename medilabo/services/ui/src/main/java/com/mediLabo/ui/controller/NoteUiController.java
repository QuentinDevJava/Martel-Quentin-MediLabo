package com.mediLabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.mediLabo.ui.dto.NoteDto;
import com.mediLabo.ui.dto.PatientDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/patients/notes")
public class NoteUiController {

	// TODO bean configuration du restTemplate au lieu de le faire dans chaque
	// controller
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${note.api.url}")
	private String noteApiUrl;

	@Value("${patient.api.url}")
	private String patientApiUrl;

	@GetMapping("/add/{patientId}")
	public String addNoteForm(@PathVariable int patientId, Model model) {
		log.info("UI GET /patients/notes/add/{} - showing add note form", patientId);
		model.addAttribute("patientNom", patientId);
		return "add-note";
	}

	@PostMapping("/add/{patientId}")
	public String submitAddNote(@PathVariable int patientId, @RequestParam("note") String noteText) {
		log.info("UI POST /patients/notes/add/{} - submitting note: {}", patientId, noteText);

		PatientDto patientDto = restTemplate.getForObject(patientApiUrl + patientId, PatientDto.class);

		NoteDto noteDto = new NoteDto();
		noteDto.setPatientId(patientId);
		noteDto.setPatientNom(patientDto.getNom());
		noteDto.setContenuNote(noteText);
		restTemplate.postForObject(noteApiUrl, noteDto, NoteDto.class);
		return "redirect:/patients";
	}

	@GetMapping("/update/{noteId}")
	public String updateNoteForm(@PathVariable String noteId, Model model) {
		log.info("UI GET /patients/notes/update/{} - showing note update form", noteId);
		NoteDto noteDto = restTemplate.getForObject(noteApiUrl + noteId, NoteDto.class);
		model.addAttribute("note", noteDto);
		return "update-note";
	}

	@PostMapping("/update/{noteId}")
	public String submitEditNote(@PathVariable String noteId, @RequestParam("note") String updatedText) {
		log.info("UI POST /patients/notes/update/{} - updating note with text: {}", noteId, updatedText);
		NoteDto noteDto = restTemplate.getForObject(noteApiUrl + noteId, NoteDto.class);
		noteDto.setContenuNote(updatedText);
		restTemplate.put(noteApiUrl + noteId, noteDto);
		return "redirect:/patients";
	}
}
