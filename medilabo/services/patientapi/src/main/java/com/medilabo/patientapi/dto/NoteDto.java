package com.medilabo.patientapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant une note médicale associée à un patient.
 */
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
