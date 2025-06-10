package com.mediLabo.ui.dto;

import lombok.Data;

@Data
public class NoteDto {

	private String id;

	private int patientId;

	private String patientNom;

	private String contenuNote;

}
