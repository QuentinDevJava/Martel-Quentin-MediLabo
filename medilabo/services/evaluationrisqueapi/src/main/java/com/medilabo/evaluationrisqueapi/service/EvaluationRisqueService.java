package com.medilabo.evaluationrisqueapi.service;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.enums.DangerLevel;

public interface EvaluationRisqueService {

	int getNumberOfTriggerTermsByPatientName(String patientName);

	PatientDto getPatientByName(String patientName);

	DangerLevel generateDiabetesReport(String patientName);

}
