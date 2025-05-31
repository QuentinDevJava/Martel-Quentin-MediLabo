package com.medilabo.evaluationrisqueapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.evaluationrisqueapi.enums.DangerLevel;
import com.medilabo.evaluationrisqueapi.service.EvaluationRisqueService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/evaluationrisque/patients/")
@AllArgsConstructor
public class EvaluationRisqueController {

	private EvaluationRisqueService evaluationRisqueService;

	@GetMapping("/name/{patientName}")
	public ResponseEntity<DangerLevel> getPatientByNom(@PathVariable String patientName) {
		log.info("Receive GET /api/evaluationrisque/patients/name/" + patientName
				+ ": EvaluationRisqueApi use RestController to send diabetes report");
		return new ResponseEntity<>(evaluationRisqueService.generateDiabetesReport(patientName), HttpStatus.OK);

	}
}
