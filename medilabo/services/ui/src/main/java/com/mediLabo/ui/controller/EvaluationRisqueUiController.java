package com.mediLabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.mediLabo.ui.dto.PatientDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/evaluationRisque")
public class EvaluationRisqueUiController {

	@Value("${patient.api.url}")
	private String patientApiUrl;

	@Value("${evaluationrisque.api.url}")
	private String evaluationRisqueApiUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/rapport/{patientId}")
	public String getRapport(@PathVariable int patientId, Model model) {
		log.info("Receive GET /evaluationrisque/patients/" + patientId
				+ ": EvaluationRisqueApi use RestController to send diabetes report");
		PatientDto patientDto = restTemplate.getForObject(patientApiUrl + patientId, PatientDto.class);

		if (patientDto == null) {
			return "redirect:/patients";
		}

		String dangerLevel = restTemplate.getForObject(evaluationRisqueApiUrl + patientId, String.class);
		dangerLevel = dangerLevel.replace("\"", "");
		log.info("Receive dannger level : " + dangerLevel);
		model.addAttribute("patient", patientDto);
		model.addAttribute("dangerLevel", dangerLevel);

		return "rapport";

	}
}