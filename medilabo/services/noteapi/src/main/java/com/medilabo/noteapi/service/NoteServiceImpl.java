package com.medilabo.noteapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.repository.NoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

	private NoteRepository noteRepository;

	@Override
	public List<NoteDto> getNotesByNom(String patientNom) {
		return noteRepository.findAllByFkPatientNom(patientNom).stream().map(this::toDto).toList();
	}

	@Override
	public NoteDto createNote(NoteDto noteDto) {
		Note note = Note.builder().fkPatientNom(noteDto.getFkPatientNom()).note(noteDto.getNote()).build();

		return toDto(noteRepository.save(note));
	}

	@Override
	public NoteDto updateNote(String id, NoteDto noteDto) {
		Note note = findById(id);
		note.setNote(noteDto.getNote());
		return toDto(noteRepository.save(note));
	}

	@Override
	public NoteDto getNotesById(String id) {
		Note note = findById(id);
		return toDto(note);
	}

	@Override
	public int getNumberOfTermsByPatient(String patientName) {

		List<NoteDto> notes = getNotesByNom(patientName);

		List<String> termes = List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur", "fumeuse",
				"anormal", "cholestérol", "vertiges", "rechute", "réaction", "anticorps");

		Set<String> foundTerms = new HashSet<>();

		for (NoteDto note : notes) {
			String content = note.getNote().toLowerCase();

			for (String terme : termes) {
				if (content.contains(terme)) {
					foundTerms.add(terme);
				}
			}
		}

		return foundTerms.size();
	}

	private Note findById(String id) {
		Optional<Note> note = noteRepository.findById(id);
		if (note.isPresent()) {
			return note.get();
		} else {
			return null;
		}
	}

	private NoteDto toDto(Note note) {
		return NoteDto.builder().id(note.getId()).fkPatientNom(note.getFkPatientNom()).note(note.getNote()).build();
	}

}
