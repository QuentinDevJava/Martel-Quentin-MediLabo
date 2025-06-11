package com.medilabo.noteapi.mapper;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoteMapper {

	public NoteDto toDto(Note note) {
		return NoteDto.builder()

				.id(note.getId())

				.patientId(note.getPatientId())

				.patientNom(note.getPatientNom())

				.contenuNote(note.getContenuNote())

				.build();
	}

	public Note toEntity(NoteDto noteDto) {
		return Note.builder()

				.id(noteDto.getId())

				.patientId(noteDto.getPatientId())

				.patientNom(noteDto.getPatientNom())

				.contenuNote(noteDto.getContenuNote())

				.build();
	}

}
