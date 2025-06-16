package com.medilabo.patientapi.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	public Patient(String nom, String prenom, LocalDate date, String genre, String adresse, String telephone) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateAnniversaire = date;
		this.genre = genre;
		this.adresse = adresse;
		this.telephone = telephone;
	}

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@Column(name = "date")
	private LocalDate dateAnniversaire;

	@Column(name = "genre")
	private String genre;

	@Column(name = "adresse")
	private String adresse;

	@Column(name = "telephone")
	private String telephone;

}
