package com.medilabo.noteapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.medilabo.noteapi.dto.NoteDto;
import com.medilabo.noteapi.entities.Note;
import com.medilabo.noteapi.repository.NoteRepository;
import com.medilabo.noteapi.service.NoteServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteServiceImpl noteService;

    private Note note;
    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
	noteService = new NoteServiceImpl(noteRepository);

	note = Note.builder().id("noteTest").patientId(1).patientNom("Jean")
		.contenuNote("Le patient est un fumeur avec du cholestérol.").build();

	noteDto = NoteDto.builder().id("noteTest").patientId(1).patientNom("Jean")
		.contenuNote("Le patient est un fumeur avec du cholestérol.").build();
    }

    @Test
    void testGetNotesByPatientId() {
	when(noteRepository.findAllByPatientId(1)).thenReturn(List.of(note));

	List<NoteDto> result = noteService.getNotesByPatientId(1);

	assertEquals(1, result.size());
	assertEquals("noteTest", result.get(0).getId());
    }

    @Test
    void testCreateNote() {
	when(noteRepository.save(any())).thenReturn(note);

	NoteDto result = noteService.createNote(noteDto);

	assertNotNull(result);
	assertEquals("noteTest", result.getId());
	verify(noteRepository).save(any());
    }

    @Test
    void testUpdateNote_successful() {
	when(noteRepository.findById("noteTest")).thenReturn(Optional.of(note));
	when(noteRepository.save(any())).thenReturn(note);

	NoteDto updatedDto = new NoteDto();
	updatedDto.setContenuNote("Nouvelle version");

	NoteDto result = noteService.updateNote("noteTest", updatedDto);

	assertNotNull(result);
	verify(noteRepository).save(any());
    }

    @Test
    void testUpdateNote_notFound_throwsException() {
	when(noteRepository.findById("not-exist")).thenReturn(Optional.empty());

	NoteDto updatedDto = new NoteDto();
	updatedDto.setContenuNote("Nouvelle version");

	assertThrows(EntityNotFoundException.class, () -> {
	    noteService.updateNote("not-exist", updatedDto);
	});
    }

    @Test
    void testGetNotesById_found() {
	when(noteRepository.findById("noteTest")).thenReturn(Optional.of(note));

	NoteDto result = noteService.getNotesById("noteTest");

	assertNotNull(result);
	assertEquals("noteTest", result.getId());
    }

    @Test
    void testGetNotesById_notFound() {
	when(noteRepository.findById("not-exist")).thenReturn(Optional.empty());

	assertThrows(EntityNotFoundException.class, () -> {
	    noteService.getNotesById("not-exist");
	});
    }

    @Test
    void testGetNumberOfTermsByPatient_detectsTermsCorrectly() {
	when(noteRepository.findAllByPatientId(1)).thenReturn(List.of(note));

	int count = noteService.getNumberOfTermsByPatient(1);

	assertEquals(2, count);
    }

    @Test
    void testGetNumberOfTermsByPatient_noTerms() {
	Note emptyNote = Note.builder().id("note-2").patientId(1).contenuNote("Le patient va bien.").build();

	when(noteRepository.findAllByPatientId(1)).thenReturn(List.of(emptyNote));

	int count = noteService.getNumberOfTermsByPatient(1);

	assertEquals(0, count);
    }
}