package com.medilabo.evaluationrisqueapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mediLabo.patientapi.entities.Patient;
import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.service.ApiClient;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class EvaluationRiqueMapper {

	@Autowired
	private ApiClient apiClient;// utilisé pour recuperer les notes associées aux patient

	public PatientDto toDtoWithNotes(Patient patient) {
		return PatientDto.builder().id(patient.getId()).nom(patient.getNom()).prenom(patient.getPrenom())
				.dateAnniversaire(patient.getDateAnniversaire()).genre(patient.getGenre()).adresse(patient.getAdresse())
				.telephone(patient.getTelephone()).notes(apiClient.getNoteDtos(patient.getNom())).build();
	}

}
