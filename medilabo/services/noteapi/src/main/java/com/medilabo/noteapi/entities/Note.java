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

	@Id
	private String id;

	private String fkPatientNom;

	private String note;

	public Note(String note, String fkPatientNom) {
		this.note = note;
		this.fkPatientNom = fkPatientNom;
	}
}
