package com.mediLabo.ui.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientDto {
	private int id;
	private String nom;
	private String prenom;
	private LocalDate dateAnniversaire;
	private String genre;
	private String adresse;
	private String telephone;

}
