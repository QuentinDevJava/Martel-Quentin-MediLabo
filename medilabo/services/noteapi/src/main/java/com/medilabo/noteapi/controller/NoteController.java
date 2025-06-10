package com.medilabo.noteapi.controller;

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

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.service.NoteService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notes/")
@AllArgsConstructor
public class NoteController {

	private NoteService noteService;

	@GetMapping("patient/{patientId}")
	public ResponseEntity<List<NoteDto>> getNoteByPatientId(@PathVariable int patientId) {
		log.info(
				"Receive GET /api/notes/patient/" + patientId + ": NoteApi use RestController to send list of NoteDto");
		return new ResponseEntity<>(noteService.getNotesByPatientId(patientId), HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<NoteDto> getNoteById(@PathVariable String id) {
		log.info("Receive GET /api/notes/" + id + ": NoteApi use RestController to send NoteDto");
		return new ResponseEntity<>(noteService.getNotesById(id), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
		NoteDto created = noteService.createNote(noteDto);
		log.info("Receive POST:" + noteDto + " - NoteApi use RestController to create NoteDto");
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<NoteDto> updateNote(@PathVariable String id, @RequestBody NoteDto noteDto) {
		NoteDto updated = noteService.updateNote(id, noteDto);
		log.info("Receive PUT /api/notes/" + id + " : NoteDto:" + noteDto
				+ " - NoteApi use RestController to update NoteDto");
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@GetMapping("termesAnalyse/{patientId}")
	public ResponseEntity<Integer> getTriggerTermCountForPatient(@PathVariable int patientId) {
		int count = noteService.getNumberOfTermsByPatient(patientId);
		log.info("Receive PUT /api/notes/termesAnalyse/" + patientId
				+ " - NoteApi use RestController get number of trigger terms in list of NoteDto for patient id : "
				+ patientId);
		return ResponseEntity.ok(count);
	}
}
