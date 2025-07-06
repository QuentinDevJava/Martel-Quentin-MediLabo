package com.medilabo.noteapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant un patient permettra de valider que le patient existe avant
 * d'ajouter la note
 */
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

}
