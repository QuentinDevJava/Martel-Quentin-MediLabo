package com.mediLabo.patientapi.service;

import java.util.List;

import com.mediLabo.patientapi.dto.PatientDto;

public interface PatientService {

	PatientDto getPatientById(int id);

	List<PatientDto> getAllPatient();

	PatientDto addPatient(PatientDto patientDto);

	PatientDto updatePatient(int id, PatientDto patientDto);

	PatientDto getPatientByName(String name);

	List<PatientDto> getPatientWithNotesById();

	// PatientDto getPatientWithNotesByName(String name);

}
