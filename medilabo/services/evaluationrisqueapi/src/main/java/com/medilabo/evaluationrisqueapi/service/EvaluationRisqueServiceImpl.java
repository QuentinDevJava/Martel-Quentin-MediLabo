package com.medilabo.evaluationrisqueapi.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.enums.DangerLevel;

@Service
public class EvaluationRisqueServiceImpl implements EvaluationRisqueService {
	@Autowired
	private NoteApi noteApi;
	@Autowired
	private PatientApi patientApi;

	@Override
	public DangerLevel generateDiabetesReport(String patientName) {

		PatientDto patientDto = getPatientByName(patientName);

		// String genre = getPatientGenreByName(patientName)

		int numberOfTriggerTerms = getNumberOfTriggerTermsByPatientName(patientName);

		int age = Period.between(patientDto.getDateAnniversaire(), LocalDate.now()).getYears();

		if (age > 30) {
			return evaluateRisqueForAgeMore30(numberOfTriggerTerms);
		} else {
			return evaluateRisqueForAge30OrLess(numberOfTriggerTerms, patientDto.getGenre());
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

//TODO il y a des cas qui ne sont pas traité cela génere des valeur inatendu ex 
	private DangerLevel evaluateRisqueForAge30OrLess(int numberOfTriggerTerms, String genre) {

		if ("M".equals(genre)) {// un homme
			switch (numberOfTriggerTerms) {
			case 0, 1 -> {// ajouté mais pas demandé
				return DangerLevel.NONE;
			}
			case 2 -> {// ajouté mais pas demandé
				return DangerLevel.BORDERLINE;
			}
			case 3, 4, 5 -> {
				return DangerLevel.IN_DANGER;
			}
			default -> {
				if (numberOfTriggerTerms > 5) {
					return DangerLevel.EARLY_ONSET;
				}
				throw new IllegalArgumentException("Unexpected value: " + numberOfTriggerTerms);
			}
			}
		} else { // une femme
			switch (numberOfTriggerTerms) {
			case 0, 1 -> {// ajouté mais pas demandé
				return DangerLevel.NONE;
			}
			case 2, 3 -> {// ajouté mais pas demandé
				return DangerLevel.BORDERLINE;
			}
			case 4, 5, 6, 7 -> {
				return DangerLevel.IN_DANGER;
			}
			default -> {
				if (numberOfTriggerTerms > 7) {
					return DangerLevel.EARLY_ONSET;
				}
				throw new IllegalArgumentException("Unexpected value: " + numberOfTriggerTerms);
			}
			}
		}
	}

	// openfeign
	@Override
	public PatientDto getPatientByName(String patientName) {
		return patientApi.getPatientDto(patientName);
	}

	// openfeign
	@Override
	public int getNumberOfTriggerTermsByPatientName(String patientName) {
		return noteApi.getTriggerTerms(patientName);
	}

}
