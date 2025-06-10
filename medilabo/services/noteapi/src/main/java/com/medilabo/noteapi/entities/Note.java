package com.medilabo.noteapi.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "notes")
public class Note {

	public Note(int patientId, String patientNom, String note) {
		super();
		this.patientId = patientId;
		this.patientNom = patientNom;
		this.contenuNote = note;
	}

	@Id
	private String id;

	private int patientId;

	private String patientNom;

	private String contenuNote;

}
