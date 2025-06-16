package com.medilabo.patientapi.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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

	@NotBlank(message = "Last name must not be blank")
	private String nom;

	@NotBlank(message = "First name must not be blank")
	private String prenom;

	@Past(message = "Birth date must be in the past")
	private LocalDate dateAnniversaire;

	@NotBlank(message = "Gender must not be blank")
	private String genre;

	private String adresse;

	private String telephone;

	List<NoteDto> notes;
}
