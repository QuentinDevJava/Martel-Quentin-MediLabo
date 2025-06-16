package com.mediabo.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

import com.mediabo.ui.dto.PatientDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/evaluationRisque")
public class EvaluationRisqueUiController {

	private final RestClient restClient;

	@Value("${patient.api.url}")
	private String patientApiUrl;

	@Value("${evaluationrisque.api.url}")
	private String evaluationRisqueApiUrl;

	@GetMapping("/rapport/{patientId}")
	public String getRapport(@PathVariable int patientId, Model model) {

		log.info("Receive GET /evaluationrisque/patients/" + patientId
				+ ": EvaluationRisqueApi use RestController to send diabetes report");

		PatientDto patientDto = restClient.get()

				.uri(patientApiUrl + patientId)

				.retrieve()

				.onStatus(HttpStatusCode::isError, (request, response) -> {

					log.error("Request to {} {} failed to retrieve patient ID {} - HTTP {}"

							, request.getMethod()

							, request.getURI()

							, patientId

							, response.getStatusCode());

					throw new RuntimeException("Error retrieving rapport");

				}).body(PatientDto.class);

		if (patientDto == null) {
			return "redirect:/patients";
		}

		String dangerLevel = restClient.get().uri(evaluationRisqueApiUrl + patientId).retrieve()

				.onStatus(HttpStatusCode::isError, (request, response) -> {

					log.error("Request to {} {} failed to retrieve patient ID {} - HTTP {}"

							, request.getMethod()

							, request.getURI()

							, patientId

							, response.getStatusCode());

					throw new RuntimeException("Error retrieving dangerLevel");

				}).body(String.class);

		dangerLevel = dangerLevel.replace("\"", "");
		log.info("Receive dannger level : " + dangerLevel);

		model.addAttribute("patient", patientDto);
		model.addAttribute("dangerLevel", dangerLevel);

		return "rapport";

	}
}