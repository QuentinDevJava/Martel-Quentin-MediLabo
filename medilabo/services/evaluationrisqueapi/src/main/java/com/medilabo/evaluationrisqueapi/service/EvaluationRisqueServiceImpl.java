package com.medilabo.evaluationrisqueapi.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.enums.DangerLevel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationRisqueServiceImpl implements EvaluationRisqueService {

    private NoteApi noteApi;
    private PatientApi patientApi;

    @Override
    public DangerLevel generateDiabetesReport(int patientId) {

	PatientDto patientDto = getPatientById(patientId);
	if (patientDto == null) {
	    throw new EntityNotFoundException("The patient is not found");
	}

	int age = Period.between(patientDto.getDateAnniversaire(), LocalDate.now()).getYears();
	String genre = patientDto.getGenre();
	int numberOfTriggerTerms = getNumberOfTriggerTermsByPatientId(patientId);

	if (age > 30) {
	    return evaluateRisqueForAgeMore30(numberOfTriggerTerms);
	} else {
	    if ("M".equals(genre)) {
		return evaluateRisqueForManAge30OrLess(numberOfTriggerTerms);
	    } else {
		return evaluateRisqueForWomanAge30OrLess(numberOfTriggerTerms);

	    }
	}
    }

    private DangerLevel evaluateRisqueForAgeMore30(int numberOfTriggerTerms) {

	switch (numberOfTriggerTerms) {
	case 0, 1 -> {
	    return DangerLevel.NONE;
	}
	case 2, 3, 4, 5 -> {
	    return DangerLevel.BORDERLINE;
	}
	case 6, 7 -> {
	    return DangerLevel.IN_DANGER;
	}
	default -> {
	    if (numberOfTriggerTerms >= 8) {
		return DangerLevel.EARLY_ONSET;
	    }
	    throw new IllegalArgumentException("Unexpected value numberOfTriggerTerms : " + numberOfTriggerTerms);
	}
	}

    }

    private DangerLevel evaluateRisqueForWomanAge30OrLess(int numberOfTriggerTerms) {
	switch (numberOfTriggerTerms) {
	case 0, 1, 2, 3 -> {
	    return DangerLevel.NONE;
	}
	case 4, 5, 6 -> {
	    return DangerLevel.IN_DANGER;
	}
	default -> {
	    if (numberOfTriggerTerms >= 7) {
		return DangerLevel.EARLY_ONSET;
	    }
	    throw new IllegalArgumentException("Unexpected value: " + numberOfTriggerTerms);
	}
	}
    }

    private DangerLevel evaluateRisqueForManAge30OrLess(int numberOfTriggerTerms) {
	switch (numberOfTriggerTerms) {
	case 0, 1, 2 -> {
	    return DangerLevel.NONE;
	}
	case 3, 4 -> {
	    return DangerLevel.IN_DANGER;
	}
	default -> {
	    if (numberOfTriggerTerms >= 5) {
		return DangerLevel.EARLY_ONSET;
	    }
	    throw new IllegalArgumentException("Unexpected value: " + numberOfTriggerTerms);
	}
	}
    }

    private PatientDto getPatientById(int patientId) {
	return patientApi.getPatientDtoForPatientId(patientId);
    }

    private int getNumberOfTriggerTermsByPatientId(int patientId) {
	return noteApi.getTriggerTermsForPatientId(patientId);
    }
}
