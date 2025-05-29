package com.mediLabo.patientapi.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDto {

	private int id;

	private String nom;

	private String prenom;

	private LocalDate dateAnniversaire;

	private String genre;

	private String adresse;

	private String telephone;

	List<NoteDto> notes;
}
