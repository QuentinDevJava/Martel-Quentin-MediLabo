package com.medilabo.evaluationrisqueapi.service;

import java.util.List;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;

public interface EvaluationRisqueService {

	PatientDto getPatientById(int id);

	List<PatientDto> getAllPatient();

	PatientDto addPatient(PatientDto patientDto);

	PatientDto updatePatient(int id, PatientDto patientDto);

	PatientDto getPatientByName(String name);

	PatientDto getPatientWithNotesById(int id);

	PatientDto getPatientWithNotesByName(String name);

}
