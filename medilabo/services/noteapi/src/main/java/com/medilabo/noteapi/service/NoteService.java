package com.medilabo.noteapi.service;

import java.util.List;

import com.medilabo.noteapi.dto.NoteDto;

public interface NoteService {

	NoteDto createNote(NoteDto noteDto);

	NoteDto updateNote(String id, NoteDto noteDto);

	NoteDto getNotesById(String id);

	int getNumberOfTermsByPatient(int patientId);

	List<NoteDto> getNotesByPatientId(int id);
}
