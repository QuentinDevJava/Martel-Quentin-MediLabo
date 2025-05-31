package com.medilabo.evaluationrisqueapi.dto;

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

	private String fkPatientNom;

	private String note;

	private int nbOfTriggerTerms;
}
