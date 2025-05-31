package com.mediLabo.ui.controller;

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
	private String patientApiUrl = "http://localhost:5005/api/patients/name/";
	private String evaluationRisqueApiUrl = "http://localhost:5003/api/evaluationrisque/patients/name/";

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/rapport/{patientName}")
	public String getRapport(@PathVariable String patientName, Model model) {
		log.info("Receive GET /evaluationrisque/patients/name/" + patientName
				+ ": EvaluationRisqueApi use RestController to send diabetes report");
		PatientDto patientDto = restTemplate.getForObject(patientApiUrl + patientName, PatientDto.class);

		if (patientDto == null) {
			return "redirect:/patients";
		}

		String dangerLevel = restTemplate.getForObject(evaluationRisqueApiUrl + patientName, String.class);
		dangerLevel = dangerLevel.replace("\"", "");
		log.info("Receive dannger level : " + dangerLevel);
		model.addAttribute("patient", patientDto);
		model.addAttribute("dangerLevel", dangerLevel);

		return "rapport";

	}
}