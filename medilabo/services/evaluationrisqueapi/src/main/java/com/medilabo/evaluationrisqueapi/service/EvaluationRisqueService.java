package com.medilabo.evaluationrisqueapi.service;

import com.medilabo.evaluationrisqueapi.enums.DangerLevel;

public interface EvaluationRisqueService {

	DangerLevel generateDiabetesReport(int patientId);
}
