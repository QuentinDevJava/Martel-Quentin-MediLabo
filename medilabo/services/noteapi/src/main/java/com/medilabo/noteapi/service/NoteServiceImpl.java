package com.medilabo.noteapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.mapper.NoteMapper;
import com.medilabo.noteapi.repository.NoteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

	private NoteMapper noteMapper;

	private NoteRepository noteRepository;

	@Override
	public List<NoteDto> getNotesByPatientId(int patientId) {
		return noteRepository.findAllByPatientId(patientId).stream().map(patient -> noteMapper.toDto(patient)).toList();
	}

	@Override
	public NoteDto createNote(NoteDto noteDto) {

		Note note = Note.builder().patientId(noteDto.getPatientId()).patientNom(noteDto.getPatientNom())
				.contenuNote(noteDto.getContenuNote()).build();

		return noteMapper.toDto(noteRepository.save(note));
	}

	@Override
	public NoteDto updateNote(String id, NoteDto updateNoteDto) {
		NoteDto noteDto = getNoteDtoWithNoteById(id);
		if (noteDto == null) {
			throw new IllegalArgumentException("The note with id : " + id + "is not found");
		} else {
			noteDto.setContenuNote(updateNoteDto.getContenuNote());

			return noteMapper.toDto(noteRepository.save(noteMapper.toEntity(noteDto)));
		}
	}

	@Override
	public NoteDto getNotesById(String id) {
		return getNoteDtoWithNoteById(id);
	}

	@Override
	public int getNumberOfTermsByPatient(int patientId) {

		List<NoteDto> notes = getNotesByPatientId(patientId);
		StringBuilder contents = new StringBuilder();
		for (NoteDto note : notes) {
			contents.append(contents.append(note.getContenuNote().toLowerCase()).append(" "));
		}
		String contentString = contents.toString();

		Map<String, List<String>> termGroups = new HashMap<String, List<String>>();
		termGroups.put("fumeur", List.of("fumer", "fumeur", "fumeuse", "fumeurs", "fumeuses"));
		termGroups.put("anormal", List.of("anormal", "anormale", "anormaux", "anormales"));
		termGroups.put("cholestérol", List.of("cholestérol"));
		termGroups.put("vertige", List.of("vertige", "vertiges"));
		termGroups.put("rechute", List.of("rechute", "rechutes"));
		termGroups.put("réaction", List.of("réaction", "réactions"));
		termGroups.put("anticorps", List.of("anticorps"));
		termGroups.put("taille", List.of("taille"));
		termGroups.put("poids", List.of("poids"));
		termGroups.put("hémoglobine a1c", List.of("hémoglobine a1c"));
		termGroups.put("microalbumine", List.of("microalbumine"));

		int count = 0;

		for (Map.Entry<String, List<String>> entry : termGroups.entrySet()) {
			for (String term : entry.getValue()) {
				if (contentString.contains(term)) {
					count++;
					log.info("Termes trouvé : " + term);
					break;
				}
			}
		}

		return count;
	}

	private NoteDto getNoteDtoWithNoteById(String noteId) {
		Optional<Note> note = noteRepository.findById(noteId);
		if (note.isPresent()) {
			return noteMapper.toDto(note.get());
		} else {
			return null;
		}
	}

}
