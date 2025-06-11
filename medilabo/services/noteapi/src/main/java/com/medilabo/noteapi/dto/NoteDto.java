package com.medilabo.noteapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {

	private String id;

	private int patientId;

	private String patientNom;

	@NotBlank(message = "Please provide content for the note")
	private String contenuNote;
}
