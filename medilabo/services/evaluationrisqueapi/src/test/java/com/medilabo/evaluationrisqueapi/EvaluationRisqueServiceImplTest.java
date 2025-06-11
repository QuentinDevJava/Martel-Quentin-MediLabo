package com.medilabo.evaluationrisqueapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.evaluationrisqueapi.dto.PatientDto;
import com.medilabo.evaluationrisqueapi.enums.DangerLevel;
import com.medilabo.evaluationrisqueapi.service.EvaluationRisqueServiceImpl;
import com.medilabo.evaluationrisqueapi.service.NoteApi;
import com.medilabo.evaluationrisqueapi.service.PatientApi;

@ExtendWith(MockitoExtension.class)
class EvaluationRisqueServiceImplTest {

	@Mock
	private NoteApi noteApi;

	@Mock
	private PatientApi patientApi;

	@InjectMocks
	private EvaluationRisqueServiceImpl evaluationService;

	private PatientDto createPatient(String gender, int age) {
		return new PatientDto("Test", "User", LocalDate.now().minusYears(age), gender);
	}

	@ParameterizedTest(name = "Gender={0}, Age={1}, Triggers={2} => Expected={3}")
	@CsvSource({ "M,45,0,NONE", "M,50,3,BORDERLINE", "F,60,6,IN_DANGER", "F,62,8,EARLY_ONSET", "M,28,4,IN_DANGER",
			"M,25,6,EARLY_ONSET", "F,27,5,IN_DANGER", "F,22,9,EARLY_ONSET", "M,25,2,NONE", "F,30,2,NONE" })
	void generateDiabetesReport_ShouldReturnExpectedDangerLevel(String gender, int age, int triggerCount,
			DangerLevel expectedLevel) {
		PatientDto patient = createPatient(gender, age);

		when(patientApi.getPatientDtoForPatientId(1)).thenReturn(patient);
		when(noteApi.getTriggerTermsForPatientId(1)).thenReturn(triggerCount);

		DangerLevel result = evaluationService.generateDiabetesReport(1);

		assertThat(result).isEqualTo(expectedLevel);
	}
}
