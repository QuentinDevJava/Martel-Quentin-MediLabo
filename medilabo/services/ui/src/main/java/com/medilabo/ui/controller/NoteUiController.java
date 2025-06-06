package com.medilabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.medilabo.ui.dto.NoteDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/patients/notes")
public class NoteUiController {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${note.api.url}")
	private String noteApiUrl;

	@GetMapping("/add/{patientNom}")
	public String addNoteForm(@PathVariable String patientNom, Model model) {
		log.info("UI GET /patients/notes/add/{} - showing add note form", patientNom);
		model.addAttribute("patientNom", patientNom);
		return "add-note";
	}

	@PostMapping("/add/{patientNom}")
	public String submitAddNote(@PathVariable String patientNom, @RequestParam("note") String noteText) {
		log.info("UI POST /patients/notes/add/{} - submitting note: {}", patientNom, noteText);
		NoteDto noteDto = new NoteDto();
		noteDto.setFkPatientNom(patientNom);
		noteDto.setNote(noteText);
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
		noteDto.setNote(updatedText);
		restTemplate.put(noteApiUrl + noteId, noteDto);
		return "redirect:/patients";
	}
}
