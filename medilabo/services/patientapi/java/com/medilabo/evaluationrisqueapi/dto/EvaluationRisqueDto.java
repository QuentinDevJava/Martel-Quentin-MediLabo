package com.medilabo.evaluationrisqueapi.dto;

import com.medilabo.evaluationrisqueapi.enums.DangerLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationRisqueDto {

	private DangerLevel dangerLevel;

}
