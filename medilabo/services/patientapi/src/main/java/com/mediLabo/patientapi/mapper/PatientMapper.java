package com.mediLabo.patientapi.mapper;

import java.util.List;

import com.mediLabo.patientapi.dto.NoteDto;
import com.mediLabo.patientapi.dto.PatientDto;
import com.mediLabo.patientapi.entities.Patient;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatientMapper {

	public PatientDto toDtoWithNotes(Patient patient, List<NoteDto> noteDto) {
		return PatientDto.builder().id(patient.getId()).nom(patient.getNom()).prenom(patient.getPrenom())
				.dateAnniversaire(patient.getDateAnniversaire()).genre(patient.getGenre()).adresse(patient.getAdresse())
				.telephone(patient.getTelephone()).notes(noteDto).build();
	}

	public PatientDto toDto(Patient patient) {
		return PatientDto.builder().id(patient.getId()).nom(patient.getNom()).prenom(patient.getPrenom())
				.dateAnniversaire(patient.getDateAnniversaire()).genre(patient.getGenre()).adresse(patient.getAdresse())
				.telephone(patient.getTelephone()).build();
	}

	public Patient toEntity(PatientDto patientDto) {
		return Patient.builder().id(patientDto.getId()).nom(patientDto.getNom()).prenom(patientDto.getPrenom())
				.dateAnniversaire(patientDto.getDateAnniversaire()).genre(patientDto.getGenre())
				.adresse(patientDto.getAdresse()).telephone(patientDto.getTelephone()).build();
	}
}
