package com.medilabo.noteapi.service;

import java.util.List;

import com.medilabo.noteapi.dto.NoteDto;

public interface NoteService {

	List<NoteDto> getNotesByNom(String nom);

	NoteDto createNote(NoteDto noteDto);

	NoteDto updateNote(String id, NoteDto noteDto);

	NoteDto getNotesById(String id);

	int getNumberOfTermsByPatient(String patientName);
}
