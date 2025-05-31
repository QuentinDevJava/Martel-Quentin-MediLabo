package com.medilabo.evaluationrisqueapi.service;

import org.springframework.stereotype.Service;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.mapper.EvaluationRiqueMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EvaluationRiqueServiceImpl implements EvaluationRisqueService {

	private EvaluationRiqueMapper mapper;

	@Override
	public PatientDto geEvaluationRiqueWithNotesById(int id) {
		return mapper.toDtoWithNotes(patient);
	}

	@Override
	public PatientDto getEvaluationRiqueSWithNotesByName(String name) {
		return mapper.toDtoWithNotes(patient);
	}

}
