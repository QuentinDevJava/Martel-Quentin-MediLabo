package com.mediLabo.patientapi.mapper;

import org.springframework.stereotype.Component;

import com.mediLabo.patientapi.dto.PatientDto;
import com.mediLabo.patientapi.entities.Patient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PatientMapper {

	public PatientDto toDto(Patient patient) {
		return PatientDto.builder().id(patient.getId()).nom(patient.getNom()).prenom(patient.getPrenom())
				.dateAnniversaire(patient.getDateAnniversaire()).genre(patient.getGenre()).adresse(patient.getAdresse())
				.telephone(patient.getTelephone()).build();
	}

	public Patient toEntity(PatientDto patientDto) {
		return Patient.builder().nom(patientDto.getNom()).prenom(patientDto.getPrenom())
				.dateAnniversaire(patientDto.getDateAnniversaire()).genre(patientDto.getGenre())
				.adresse(patientDto.getAdresse()).telephone(patientDto.getTelephone()).build();
	}
}
