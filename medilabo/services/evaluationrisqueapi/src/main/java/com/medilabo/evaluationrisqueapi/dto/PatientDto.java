package com.medilabo.evaluationrisqueapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

	private int id;

	private String nom;

	private String prenom;

	private LocalDate dateAnniversaire;

	private String genre;

	public PatientDto(String nom, String prenom, LocalDate dateAnniversaire, String genre) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateAnniversaire = dateAnniversaire;
		this.genre = genre;
	}
}
