package com.medilabo.ui.dto;

import lombok.Data;

@Data
public class NoteDto {
	private String id;

	private String fkPatientNom;

	private String note;

}
