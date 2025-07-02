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

    @GetMapping("/{patientId}")
    public ResponseEntity<DangerLevel> getDiabetesReportWithpatientId(@PathVariable int patientId) {
	log.info("get evaluation risque for patient with id {}", patientId);
	try {
	    return new ResponseEntity<>(evaluationRisqueService.generateDiabetesReport(patientId), HttpStatus.OK);
	} catch (Exception e) {
	    log.error("Error while generate diabete Report", e);
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
    }
}
