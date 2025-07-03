package com.medilabo.noteapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.mapper.NoteMapper;
import com.medilabo.noteapi.repository.NoteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service de gestion des notes médicales. Fournit les opérations CRUD et le
 * calcul du nombre de mots-clés liés à des termes médicaux dans les notes.
 */
@Slf4j
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteMapper noteMapper = new NoteMapper();
    private final NoteRepository noteRepository;

    /**
     * Récupère toutes les notes associées à un patient.
     *
     * @param patientId identifiant du patient
     * @return liste des NoteDto associées
     */
    @Override
    public List<NoteDto> getNotesByPatientId(int patientId) {
	return noteRepository.findAllByPatientId(patientId).stream().map(noteMapper::toDto).toList();
    }

    /**
     * Crée une nouvelle note médicale pour un patient.
     *
     * @param noteDto données de la note à créer
     * @return note créée sous forme de NoteDto
     */
    @Override
    public NoteDto createNote(NoteDto noteDto) {
	Note note = Note.builder().patientId(noteDto.getPatientId()).patientNom(noteDto.getPatientNom())
		.contenuNote(noteDto.getContenuNote()).build();

	return noteMapper.toDto(noteRepository.save(note));
    }

    /**
     * Met à jour une note existante.
     *
     * @param id            identifiant de la note à mettre à jour
     * @param updateNoteDto nouvelles données de la note
     * @return note mise à jour sous forme de NoteDto
     * @throws EntityNotFoundException si la note n'existe pas
     */
    @Override
    public NoteDto updateNote(String id, NoteDto updateNoteDto) {
	NoteDto noteDto = getNoteDtoWithNoteById(id);
	if (noteDto == null) {
	    throw new EntityNotFoundException("The note is not found");
	} else {
	    noteDto.setContenuNote(updateNoteDto.getContenuNote());
	    return noteMapper.toDto(noteRepository.save(noteMapper.toEntity(noteDto)));
	}
    }

    /**
     * Récupère une note par son identifiant.
     *
     * @param id identifiant de la note
     * @return NoteDto correspondante ou null si non trouvée
     */
    @Override
    public NoteDto getNotesById(String id) {
	return getNoteDtoWithNoteById(id);
    }

    /**
     * Calcule le nombre de termes médicaux détectés dans les notes d’un patient.
     * Les termes sont regroupés et comptés une seule fois par groupe.
     *
     * @param patientId identifiant du patient
     * @return nombre de groupes de termes médicaux trouvés
     * @throws EntityNotFoundException si aucun patient n'est trouvé
     */
    @Override
    public int getNumberOfTermsByPatient(int patientId) {
	log.info("get number of terms");
	int count = 0;

	List<NoteDto> notes = getNotesByPatientId(patientId);

	if (notes == null) {
	    return count;
	}

	StringBuilder contents = new StringBuilder();
	for (NoteDto note : notes) {
	    contents.append(note.getContenuNote().toLowerCase()).append(" ");
	}

	String contentString = contents.toString();

	Map<String, List<String>> termGroups = new HashMap<>();
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

	for (Map.Entry<String, List<String>> entry : termGroups.entrySet()) {
	    for (String term : entry.getValue()) {
		if (contentString.contains(term)) {
		    count++;
		    log.info("Terme : " + term);
		    break;
		}
	    }
	}
	log.info("Number of terms : {}", count);
	return count;
    }

    private NoteDto getNoteDtoWithNoteById(String noteId) {
	return noteRepository.findById(noteId).map(noteMapper::toDto)
		.orElseThrow(() -> new EntityNotFoundException("No note found for id " + noteId));
    }
}
