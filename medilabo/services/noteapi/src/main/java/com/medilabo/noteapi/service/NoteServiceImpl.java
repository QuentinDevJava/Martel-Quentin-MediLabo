package com.medilabo.noteapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.repository.NoteRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

	private NoteRepository noteRepository;

	@Override
	public List<NoteDto> getNotesByPatientId(int patientId) {
		return noteRepository.findAllByPatientId(patientId).stream().map(this::toDto).toList();
	}

	@Override
	public NoteDto createNote(NoteDto noteDto) {
		Note note = Note.builder().patientId(noteDto.getPatientId()).patientNom(noteDto.getPatientNom())
				.contenuNote(noteDto.getContenuNote()).build();

		return toDto(noteRepository.save(note));
	}

	@Override
	public NoteDto updateNote(String id, NoteDto updateNoteDto) {
		NoteDto noteDto = getNoteDtoByNoteById(id);
		if (noteDto == null) {
			throw new IllegalArgumentException("The note with id : " + id + "is not found");
		} else {
			noteDto.setContenuNote(updateNoteDto.getContenuNote());

			return toDto(noteRepository.save(toEntity(noteDto)));
		}
	}

	@Override
	public NoteDto getNotesById(String id) {
		return getNoteDtoByNoteById(id);

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

	private NoteDto getNoteDtoByNoteById(String id) {
		Optional<Note> note = noteRepository.findById(id);
		if (note.isPresent()) {
			return toDto(note.get());
		} else {
			return null;
		}
	}

	// TODO class mapper ?
	private NoteDto toDto(Note note) {
		return NoteDto.builder()

				.id(note.getId())

				.patientId(note.getPatientId())

				.patientNom(note.getPatientNom())

				.contenuNote(note.getContenuNote())

				.build();
	}

	// TODO class mapper ?
	private Note toEntity(NoteDto noteDto) {
		return Note.builder()

				.id(noteDto.getId())

				.patientId(noteDto.getPatientId())

				.patientNom(noteDto.getPatientNom())

				.contenuNote(noteDto.getContenuNote())

				.build();
	}

}
