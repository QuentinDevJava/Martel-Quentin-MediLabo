package com.mediLabo.patientapi.dto;

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

	private String contenuNote;
}
