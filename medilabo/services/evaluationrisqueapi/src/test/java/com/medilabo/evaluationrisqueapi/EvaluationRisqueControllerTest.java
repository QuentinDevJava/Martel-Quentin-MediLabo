package com.medilabo.evaluationrisqueapi;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.medilabo.evaluationrisqueapi.enums.DangerLevel;
import com.medilabo.evaluationrisqueapi.security.client.AuthClient;
import com.medilabo.evaluationrisqueapi.service.EvaluationRisqueService;

import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EvaluationRisqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EvaluationRisqueService evaluationRisqueService;
    @MockitoBean
    private AuthClient authClient;

    @Test
    void getDiabetesReportWithpatientId_ShouldReturnDangerLevel() throws Exception {
	int patientId = 1;
	DangerLevel expectedLevel = DangerLevel.IN_DANGER;

	when(authClient.validateToken(anyString())).thenReturn(Mono.just(true));
	when(evaluationRisqueService.generateDiabetesReport(patientId)).thenReturn(expectedLevel);

	mockMvc.perform(get("/api/evaluationrisque/patients/{patientId}", patientId).header("Authorization",
		"Bearer test-token")).andExpect(status().isOk()).andExpect(content().json("\"IN_DANGER\""));
    }

}
