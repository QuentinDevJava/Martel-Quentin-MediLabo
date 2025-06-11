package com.mediLabo.patientapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.mediLabo.patientapi.dto.NoteDto;
import com.mediLabo.patientapi.dto.PatientDto;
import com.mediLabo.patientapi.entities.Patient;
import com.mediLabo.patientapi.repository.PatientRepository;
import com.mediLabo.patientapi.service.NoteApi;
import com.mediLabo.patientapi.service.PatientServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private NoteApi noteApi;

	private PatientServiceImpl patientServiceImpl;

	private Patient testPatient;
	private PatientDto testPatientDto;
	private Optional<Patient> optionalPatient;

	@BeforeEach
	void setUp() {

		patientServiceImpl = new PatientServiceImpl(patientRepository, noteApi);

		testPatient = new Patient();
		testPatient.setId(1);
		testPatient.setNom("Test");
		testPatient.setPrenom("Jean");
		testPatient.setDateAnniversaire(LocalDate.of(1990, 1, 1));
		testPatient.setGenre("M");
		testPatient.setAdresse("123 rue Test");
		testPatient.setTelephone("0000000000");

		optionalPatient = Optional.of(testPatient);

		testPatientDto = new PatientDto();
		testPatientDto.setId(1);
		testPatientDto.setNom("Test");
		testPatientDto.setPrenom("Jean");
		testPatientDto.setDateAnniversaire(LocalDate.of(1990, 1, 1));
		testPatientDto.setGenre("M");
		testPatientDto.setAdresse("123 rue Test");
		testPatientDto.setTelephone("0000000000");
	}

	@Test
	void testGetPatientById() {
		when(patientRepository.findById(1)).thenReturn(optionalPatient);

		PatientDto result = patientServiceImpl.getPatientById(1);

		assertEquals("Test", result.getNom());
		assertEquals("Jean", result.getPrenom());
		verify(patientRepository).findById(1);
	}

	@Test
	void testGetAllPatient() {
		when(patientRepository.findAll()).thenReturn(List.of(testPatient));

		List<PatientDto> result = patientServiceImpl.getAllPatient();

		assertEquals(1, result.size());
		assertEquals("Test", result.get(0).getNom());
	}

	@Test
	void testAddPatient() {
		when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

		PatientDto result = patientServiceImpl.addPatient(testPatientDto);

		assertEquals("Test", result.getNom());
		verify(patientRepository).save(any(Patient.class));
	}

	@Test
	void testUpdatePatient() {
		when(patientRepository.findById(1)).thenReturn(optionalPatient);
		when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

		PatientDto updatedDto = new PatientDto();
		updatedDto.setNom("Nouveau");
		updatedDto.setPrenom("Prenom");
		updatedDto.setDateAnniversaire(LocalDate.of(2000, 1, 1));
		updatedDto.setGenre("F");
		updatedDto.setAdresse("456 rue nouvelle");
		updatedDto.setTelephone("9999999999");

		PatientDto result = patientServiceImpl.updatePatient(1, updatedDto);

		assertEquals("Nouveau", result.getNom());
		assertEquals("Prenom", result.getPrenom());
		verify(patientRepository).save(any(Patient.class));
	}

	@Test
	void testGetPatientWithNotesById() {
		when(patientRepository.findAll()).thenReturn(List.of(optionalPatient.get()));

		List<NoteDto> notes = List.of(new NoteDto("note-1", 1, "Test", "Ceci est une note"),
				new NoteDto("note-2", 1, "Test", "Deuxième note"));

		when(noteApi.getNoteDtos(1)).thenReturn(notes);

		List<PatientDto> result = patientServiceImpl.getAllPatientsWithNotes();

		assertEquals("Test", result.getFirst().getNom());
		assertEquals(2, result.getFirst().getNotes().size());
		verify(patientRepository).findAll();
		verify(noteApi).getNoteDtos(1);
	}

}
