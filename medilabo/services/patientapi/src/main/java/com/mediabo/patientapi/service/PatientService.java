package com.mediabo.patientapi.service;

import java.util.List;

import com.mediabo.patientapi.dto.PatientDto;

public interface PatientService {

	PatientDto getPatientById(int id);

	List<PatientDto> getAllPatient();

	PatientDto addPatient(PatientDto patientDto);

	PatientDto updatePatient(int id, PatientDto patientDto);

	PatientDto getPatientWithNotesById(int id);

	List<PatientDto> getAllPatientsWithNotes();

}
